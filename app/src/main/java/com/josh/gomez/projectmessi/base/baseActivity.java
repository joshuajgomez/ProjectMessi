package com.josh.gomez.projectmessi.base;

import androidx.appcompat.app.AppCompatActivity;

import com.josh.gomez.projectmessi.generic.repository.Constants;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class baseActivity extends AppCompatActivity {

    public void setBaseActivity() {
        Constants.context = this;
    }

}
