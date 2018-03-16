package com.josh.gomez.projectmessi.service.rebootReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.josh.gomez.projectmessi.service.alarmReceiver.AlarmManager;

/**
 * Created by Joshua Gomez on 26/02/2018.
 */

public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager.initAlarmManager(context);
    }
}
