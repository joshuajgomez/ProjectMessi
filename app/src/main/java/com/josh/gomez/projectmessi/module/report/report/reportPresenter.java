package com.josh.gomez.projectmessi.module.report.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.josh.gomez.projectmessi.generic.database.AppDatabase;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class reportPresenter implements reportContract.action {

    Activity activity;
    reportContract.view mView;

    public reportPresenter(Activity activity) {
        this.activity = activity;
        mView = (reportContract.view) activity;

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getData() {
        new AsyncTask<Void, Void, List<reportModel>>() {
            @Override
            protected List<reportModel> doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getAll();
                List<reportModel> reportModelList = getMonthYearList(messList);
                return reportModelList;
            }

            @Override
            protected void onPostExecute(List<reportModel> reportModels) {
                super.onPostExecute(reportModels);
                if (reportModels != null && reportModels.size() > 0)
                    mView.setData(reportModels);
                else mView.showNoData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private List<Integer> getMonthList(List<Mess> messList) {
        if (messList == null || messList.size() < 1) return null;
        List<Integer> monthList = new ArrayList<>();
        monthList.add(DateUtil.getMonthFromDate(messList.get(0).date));
        for (int i = 1; i < messList.size(); i++) {
            if (!monthList.contains(DateUtil.getMonthFromDate(messList.get(i).date)))
                monthList.add(DateUtil.getMonthFromDate(messList.get(i).date));
        }
        return monthList;
    }

    private List<reportModel> getMonthYearList(List<Mess> messList) {
        if (messList == null || messList.size() < 1) return null;
        List<reportModel> monthList = new ArrayList<>();
        reportModel reportModel = new reportModel();
        reportModel.month = DateUtil.getMonthFromDate(messList.get(0).date);
        reportModel.year = DateUtil.getYearFromDate(messList.get(0).date);
        monthList.add(reportModel);
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
                reportModel reportModel1 = new reportModel();
                reportModel1.year = DateUtil.getYearFromDate(messList.get(i).date);
                reportModel1.month = DateUtil.getMonthFromDate(messList.get(i).date);
                monthList.add(reportModel1);
            }
        }
        return monthList;
    }

}
