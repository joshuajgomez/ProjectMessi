package com.josh.gomez.projectmessi.module.messEntry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.josh.gomez.projectmessi.generic.database.AppDatabase;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;

import java.util.List;

/**
 * Created by Joshua Gomez on 13/01/2018.
 */

public class MessEntryPresenter implements MessEntryContract.action {

    MessEntryContract.view mView;
    Activity activity;

    public MessEntryPresenter(Activity activity) {
        this.activity = activity;
        mView = (MessEntryContract.view) activity;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveData(final Mess Mess) {
        new AsyncTask<Mess, Void, Void>() {

            @Override
            protected Void doInBackground(Mess... Messes) {
                Mess mess1 = Messes[0];
                AppDatabase database = AppDatabase.getInstance(activity);
                if (mess1.breakfast || mess1.lunch || mess1.dinner)
                    database.messDao().addMess(mess1);
                else {
                    List<Mess> list = database.messDao().getMessByDate(mess1.date);
                    if (list != null && list.size() > 0)
                        database.messDao().deleteMess(mess1.date);
                }
                AppDatabase.destroyInstance();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.showToast("Mess updated");
                activity.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Mess);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getData(String date) {
        new AsyncTask<String, Void, Mess>() {
            @Override
            protected Mess doInBackground(String... strings) {
                AppDatabase database = AppDatabase.getInstance(activity);
                List<Mess> messList = database.messDao().getMessByDate(strings[0]);
                AppDatabase.destroyInstance();
                if (messList != null && messList.size() > 0)
                    return messList.get(0);
                else return new Mess();
            }

            @Override
            protected void onPostExecute(Mess Mess) {
                super.onPostExecute(Mess);
                mView.showData(Mess);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, date);
    }
}
