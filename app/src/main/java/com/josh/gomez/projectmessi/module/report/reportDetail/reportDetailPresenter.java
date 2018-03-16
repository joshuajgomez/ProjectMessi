package com.josh.gomez.projectmessi.module.report.reportDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.josh.gomez.projectmessi.generic.database.AppDatabase;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;
import com.josh.gomez.projectmessi.generic.utils.pdf.BuildPDF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 16/01/2018.
 */

public class reportDetailPresenter implements reportDetailContract.action {

    Activity activity;
    reportDetailContract.view mView;

    public reportDetailPresenter(reportDetailFragment reportDetailFragment) {
        this.activity = reportDetailFragment.getActivity();
        mView = (reportDetailContract.view) reportDetailFragment;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getData(int month, int year) {
        new AsyncTask<Integer, Void, reportDetailModel>() {
            @Override
            protected reportDetailModel doInBackground(Integer... integers) {
                int month = integers[0];
                int year = integers[1];
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = getMessByMonth(month, year, database.messDao().getAll());
                reportDetailModel reportDetailModel = new reportDetailModel();
                reportDetailModel.totalRate = getTotalRate(messList);
                reportDetailModel.totalBreakfastRate = getTotalBreakfastRate(messList);
                reportDetailModel.totalLunchRate = getTotalLunchRate(messList);
                reportDetailModel.totalDinnerRate = getTotalDinnerRate(messList);
                reportDetailModel.totalLunchCount = getTotalLunchCount(messList);
                reportDetailModel.totalDinnerCount = getTotalDinnerCount(messList);
                reportDetailModel.totalBreakfastCount = getTotalBreakfastCount(messList);
                reportDetailModel.messList = messList;
                return reportDetailModel;
            }

            @Override
            protected void onPostExecute(reportDetailModel reportDetailModel) {
                super.onPostExecute(reportDetailModel);
                mView.showData(reportDetailModel);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, month, year);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void downloadPdf(int month, int year) {
        new AsyncTask<Integer, Void, reportDetailModel>() {
            @Override
            protected reportDetailModel doInBackground(Integer... integers) {
                int month = integers[0];
                int year = integers[1];
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = getMessByMonth(month, year, database.messDao().getAll());
                reportDetailModel reportDetailModel = new reportDetailModel();
                reportDetailModel.totalRate = getTotalRate(messList);
                reportDetailModel.messList = messList;
                reportDetailModel.month = month;
                reportDetailModel.year = year;
                reportDetailModel.totalBreakfastRate = getTotalBreakfastRate(messList);
                reportDetailModel.totalLunchRate = getTotalLunchRate(messList);
                reportDetailModel.totalDinnerRate = getTotalDinnerRate(messList);
                reportDetailModel.totalLunchCount = getTotalLunchCount(messList);
                reportDetailModel.totalDinnerCount = getTotalDinnerCount(messList);
                reportDetailModel.totalBreakfastCount = getTotalBreakfastCount(messList);
                reportDetailModel.messList = messList;
                return reportDetailModel;
            }

            @Override
            protected void onPostExecute(reportDetailModel reportDetailModel) {
                super.onPostExecute(reportDetailModel);
                BuildPDF pdf = new BuildPDF(activity);
                pdf
                        .title(DateUtil.getMonthWord(reportDetailModel.month) + " " + reportDetailModel.year + " Mess Report")
                        .load(reportDetailModel.messList)
                        .totalRate(reportDetailModel.totalRate)
                        .Build();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, month, year);
    }

    private List<Mess> getMessByMonth(int month, int year, List<Mess> messList) {
        List<Mess> monthMesses = new ArrayList<>();
        for (int i = 0; i < messList.size(); i++) {
            if (DateUtil.getMonthFromDate(messList.get(i).date) == month
                    && DateUtil.getYearFromDate(messList.get(i).date) == year)
                monthMesses.add(messList.get(i));
        }
        return monthMesses;
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
}
