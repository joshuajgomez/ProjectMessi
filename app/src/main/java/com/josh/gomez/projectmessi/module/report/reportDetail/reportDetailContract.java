package com.josh.gomez.projectmessi.module.report.reportDetail;

/**
 * Created by Joshua Gomez on 16/01/2018.
 */

public class reportDetailContract {

    public interface view {
        void showData(reportDetailModel reportDetailModel);
    }

    public interface action {
        void getData(int month, int year);

        void downloadPdf(int month, int year);
    }
}
