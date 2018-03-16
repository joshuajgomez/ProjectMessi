package com.josh.gomez.projectmessi.module.userInfo;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class UserContract {

    public interface view {
        void showData(UserModel userModel);

        void onUpdateSuccess();

        void updateProgress(int progress, int total);

        void hideProgress();
    }

    public interface action {
        void getData();

        void updateUsername(String name);

        void printPdf();

        void updateRates(int breakfast, int lunch, int dinner);

        void startExporting();

        void startImporting();
    }
}
