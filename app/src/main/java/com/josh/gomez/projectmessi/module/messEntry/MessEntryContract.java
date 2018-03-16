package com.josh.gomez.projectmessi.module.messEntry;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;

/**
 * Created by Joshua Gomez on 13/01/2018.
 */

public class MessEntryContract {

    public interface view {
        void showToast(String message);

        void showData(Mess Mess);
    }

    public interface action {
        void saveData(Mess Mess);

        void getData(String date);
    }

}
