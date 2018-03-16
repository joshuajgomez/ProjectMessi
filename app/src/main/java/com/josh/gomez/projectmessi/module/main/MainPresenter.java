package com.josh.gomez.projectmessi.module.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.josh.gomez.projectmessi.generic.database.AppDatabase;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.repository.sampleData;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.module.main.spinner.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bayasys on 13/01/2018.
 */

public class MainPresenter implements MainContract.action {

    Activity activity;
    MainContract.view mView;

    public MainPresenter(Activity activity) {
        this.activity = activity;
        this.mView = (MainContract.view) activity;
    }

    public void getCurrentMonthData() {
        String date = DateUtil.getCurrentDate();
        int month = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        getData(month, year);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getData(int month, int year) {
        new AsyncTask<Integer, Void, List<Mess>>() {
            @Override
            protected List<Mess> doInBackground(Integer... voids) {
                int month = voids[0];
                int year = voids[1];
                AppDatabase database = AppDatabase.getInstance(activity);
                return filterData(month, year, database.messDao().getAll());
            }

            @Override
            protected void onPostExecute(List<Mess> messList) {
                super.onPostExecute(messList);
                if (messList != null && messList.size() > 0)
                    mView.showData(messList, DateUtil.getTotalDaysInMonth(Integer.parseInt(messList.get(0).date.split("/")[1])));
                else mView.showEmptyData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, month, year);
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public void clearData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(activity);
                database.messDao().clearMess();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getCurrentMonthData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getSpinnerData() {
        new AsyncTask<Void, Void, List<SpinnerModel>>() {
            @Override
            protected List<SpinnerModel> doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getAll();
                List<SpinnerModel> spinnerModelList = getMonthYearList(messList);
                return spinnerModelList;
            }

            @Override
            protected void onPostExecute(List<SpinnerModel> spinnerModelList) {
                super.onPostExecute(spinnerModelList);
                if (spinnerModelList != null && spinnerModelList.size() > 0)
                    mView.setSpinner(spinnerModelList);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private List<SpinnerModel> getMonthYearList(List<Mess> messList) {
        if (messList == null || messList.size() < 1) return null;
        List<SpinnerModel> monthList = new ArrayList<>();
        SpinnerModel spinnerModel = new SpinnerModel();
        spinnerModel.month = DateUtil.getMonthFromDate(messList.get(0).date);
        spinnerModel.year = DateUtil.getYearFromDate(messList.get(0).date);
        monthList.add(spinnerModel);
        for (int i = 1; i < messList.size(); i++) {
            boolean MATCH_FOUND = false;
            for (int j = 0; j < monthList.size(); j++) {
                if (monthList.get(j).year != DateUtil.getYearFromDate(messList.get(i).date)
                        || monthList.get(j).month != DateUtil.getMonthFromDate(messList.get(i).date)) {
                    MATCH_FOUND = false;
                } else {
                    MATCH_FOUND = true;
                    break;
                }
            }
            if (!MATCH_FOUND) {
                SpinnerModel spinnerModel1 = new SpinnerModel();
                spinnerModel1.year = DateUtil.getYearFromDate(messList.get(i).date);
                spinnerModel1.month = DateUtil.getMonthFromDate(messList.get(i).date);
                monthList.add(spinnerModel1);
            }
        }
        return monthList;
    }

}
