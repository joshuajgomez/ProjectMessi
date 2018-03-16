package com.josh.gomez.projectmessi.module.main;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.module.main.spinner.SpinnerModel;

import java.util.List;

/**
 * Created by bayasys on 13/01/2018.
 */

public class MainContract {

    public interface action {
        void getData(int month, int year);

        void getSpinnerData();

        void clearData();

    }

    public interface view {
        void showData(List<Mess> messList, int noOfDays);

        void showEmptyData();

        void setSpinner(List<SpinnerModel> spinnerModel);
    }

}
