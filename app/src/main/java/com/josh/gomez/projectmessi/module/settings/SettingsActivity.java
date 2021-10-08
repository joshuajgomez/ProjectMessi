package com.josh.gomez.projectmessi.module.settings;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.repository.Constants;

import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        loadFragment();
        Constants.context = this;
    }

    private void loadFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new SettingsFragment())
                .commit();
    }
}
