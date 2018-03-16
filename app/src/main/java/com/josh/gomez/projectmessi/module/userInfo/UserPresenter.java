package com.josh.gomez.projectmessi.module.userInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.josh.gomez.projectmessi.generic.converter.MessConverter;
import com.josh.gomez.projectmessi.generic.database.AppDatabase;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.FileUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;
import com.josh.gomez.projectmessi.generic.utils.pdf.BuildPDF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class UserPresenter implements UserContract.action {

    UserContract.view mView;
    Activity activity;
    public static final String DATE_KEY = "DATE_KEY";
    public static final String BREAKFAST_KEY = "BREAKFAST_KEY";
    public static final String LUNCH_KEY = "LUNCH_KEY";
    public static final String DINNER_KEY = "DINNER_KEY";
    private static final String mess_array = "mess_array";
    private static final String username = "username";
    private static final String breakfast_rate = "breakfast_rate";
    private static final String lunch_rate = "lunch_rate";
    private static final String dinner_rate = "dinner_rate";

    public UserPresenter(Activity activity) {
        this.activity = activity;
        mView = (UserContract.view) activity;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getData() {
        new AsyncTask<Void, Void, UserModel>() {
            @Override
            protected UserModel doInBackground(Void... voids) {
                UserModel userModel = new UserModel();
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getAll();
                messList = filterData(DateUtil.getMonthFromDate(DateUtil.getCurrentDate()), DateUtil.getYearFromDate(DateUtil.getCurrentDate()), messList);
                userModel.userName = SharedUtil.getUsername();
                userModel.totalRate = getTotalRate(messList);
                userModel.totalBreakfastRate = getTotalBreakfastRate(messList);
                userModel.totalLunchRate = getTotalLunchRate(messList);
                userModel.totalDinnerRate = getTotalDinnerRate(messList);
                userModel.totalMealCount = getTotalMealCount(messList);
                userModel.totalLunchCount = getTotalLunchCount(messList);
                userModel.totalDinnerCount = getTotalDinnerCount(messList);
                userModel.totalBreakfastCount = getTotalBreakfastCount(messList);
                AppDatabase.destroyInstance();
                return userModel;
            }

            @Override
            protected void onPostExecute(UserModel userModel) {
                super.onPostExecute(userModel);
                mView.showData(userModel);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private List<Mess> filterData(int month, int year, List<Mess> messList) {
        List<Mess> newMessList = new ArrayList<>();
        for (int i = 0; i < messList.size(); i++) {
            Mess mess = messList.get(i);
            int month1 = Integer.parseInt(mess.date.split("/")[1]);
            int year1 = Integer.parseInt(mess.date.split("/")[2]);
            if (month == month1 && year == year1)
                newMessList.add(mess);
        }
        return newMessList;
    }

    @Override
    public void updateUsername(String name) {
        SharedUtil.setUsername(name);
        mView.onUpdateSuccess();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void printPdf() {
        new AsyncTask<Void, Void, UserModel>() {
            @Override
            protected UserModel doInBackground(Void... voids) {
                UserModel userModel = new UserModel();
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getAll();
                userModel.month = DateUtil.getMonthFromDate(DateUtil.getCurrentDate());
                userModel.year = DateUtil.getYearFromDate(DateUtil.getCurrentDate());
                messList = filterData(userModel.month, userModel.year, messList);
                userModel.userName = SharedUtil.getUsername();
                userModel.totalRate = getTotalRate(messList);
                userModel.totalBreakfastRate = getTotalBreakfastRate(messList);
                userModel.totalLunchRate = getTotalLunchRate(messList);
                userModel.totalDinnerRate = getTotalDinnerRate(messList);
                userModel.totalMealCount = getTotalMealCount(messList);
                userModel.totalLunchCount = getTotalLunchCount(messList);
                userModel.totalDinnerCount = getTotalDinnerCount(messList);
                userModel.totalBreakfastCount = getTotalBreakfastCount(messList);
                userModel.messList = messList;
                AppDatabase.destroyInstance();
                return userModel;
            }

            @Override
            protected void onPostExecute(UserModel userModel) {
                super.onPostExecute(userModel);
                BuildPDF pdf = new BuildPDF(activity);
                pdf
                        .title("Mess Report " + DateUtil.getMonthWord(userModel.month) + " " + userModel.year)
                        .load(userModel.messList)
                        .totalRate(userModel.totalRate)
                        .Build();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private int getTotalRate(List<Mess> messList) {
        int cost = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.breakfast) cost += SharedUtil.getBreakfastCost();
            if (Mess.lunch) cost += SharedUtil.getLunchCost();
            if (Mess.dinner) cost += SharedUtil.getDinnerCost();
        }
        return cost;
    }

    private int getTotalBreakfastRate(List<Mess> messList) {
        int cost = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.breakfast) cost += SharedUtil.getBreakfastCost();
        }
        return cost;
    }

    private int getTotalLunchRate(List<Mess> messList) {
        int cost = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.lunch) cost += SharedUtil.getLunchCost();
        }
        return cost;
    }

    private int getTotalDinnerRate(List<Mess> messList) {
        int cost = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.dinner) cost += SharedUtil.getDinnerCost();
        }
        return cost;
    }

    private int getTotalMealCount(List<Mess> messList) {
        int count = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.breakfast) count++;
            if (Mess.lunch) count++;
            if (Mess.dinner) count++;
        }
        return count;
    }

    private int getTotalBreakfastCount(List<Mess> messList) {
        int count = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.breakfast) count++;
        }
        return count;
    }

    private int getTotalLunchCount(List<Mess> messList) {
        int count = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.lunch) count++;
        }
        return count;
    }

    private int getTotalDinnerCount(List<Mess> messList) {
        int count = 0;
        for (int i = 0; i < messList.size(); i++) {
            Mess Mess = messList.get(i);
            if (Mess.dinner) count++;
        }
        return count;
    }

    public void updateRates(int breakfast, int lunch, int dinner) {
        SharedUtil.setBreakfastCost(breakfast);
        SharedUtil.setLunchCost(lunch);
        SharedUtil.setDinnerCost(dinner);
        mView.onUpdateSuccess();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void startExporting() {
        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getAll();
                JSONArray array = new JSONArray();
                for (int i = 0; i < messList.size(); i++) {
//                    SystemClock.sleep(1000);
                    JSONObject object = new JSONObject();
                    Mess mess = messList.get(i);
                    try {
                        object.put(DATE_KEY, mess.date);
                        object.put(BREAKFAST_KEY, mess.breakfast);
                        object.put(LUNCH_KEY, mess.lunch);
                        object.put(DINNER_KEY, mess.dinner);
                        array.put(object);
                        publishProgress(i + 1, messList.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject object = new JSONObject();
                try {
                    object.put(mess_array, array);
                    object.put(username, SharedUtil.getUsername());
                    object.put(breakfast_rate, SharedUtil.getBreakfastCost());
                    object.put(lunch_rate, SharedUtil.getLunchCost());
                    object.put(dinner_rate, SharedUtil.getDinnerCost());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FileUtil.writeToFile("mess_mate_backup.txt", object.toString(), activity);
                AppDatabase.destroyInstance();
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int progress = values[0];
                int total = values[1];
                mView.updateProgress(progress, total);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.hideProgress();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void startImporting() {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String importData = FileUtil.readFromFile("mess_mate_backup.txt", activity);
                AppDatabase database = AppDatabase.getInstance(activity);
                try {
                    JSONObject object = new JSONObject(importData);
                    JSONArray array = object.getJSONArray(mess_array);
                    SharedUtil.setUsername(object.getString(username));
                    SharedUtil.setBreakfastCost(object.getInt(breakfast_rate));
                    SharedUtil.setLunchCost(object.getInt(lunch_rate));
                    SharedUtil.setDinnerCost(object.getInt(dinner_rate));
                    List<Mess> messList = MessConverter.convert(array);
                    for (int i = 0; i < messList.size(); i++) {
//                        SystemClock.sleep(1000);
                        database.messDao().addMess(messList.get(i));
                        publishProgress(i, messList.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppDatabase.destroyInstance();
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int progress = values[0];
                int total = values[1];
                mView.updateProgress(progress, total);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.hideProgress();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
