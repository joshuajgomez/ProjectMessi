package com.josh.gomez.projectmessi.module.report.report;

import java.util.List;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class reportContract {

    public interface action {
        void getData();
    }

    public interface view {
        void setData(List<reportModel> reportModels);
        void showNoData();
    }

}
