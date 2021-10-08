package com.josh.gomez.projectmessi.module.splashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.module.main.MainView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initTimer();
    }

    @SuppressLint("StaticFieldLeak")
    private void initTimer() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ActivityCompat.finishAffinity(SplashActivity.this);
                startActivity(new Intent(SplashActivity.this, MainView.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
