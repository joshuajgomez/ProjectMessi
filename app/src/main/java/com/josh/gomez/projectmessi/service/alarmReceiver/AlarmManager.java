package com.josh.gomez.projectmessi.service.alarmReceiver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import java.util.Calendar;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class AlarmManager {

    private static android.app.AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void initAlarmManager(Context context, String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
        setAlarm(context, calendar);
    }

    public static void initAlarmManager(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(SharedUtil.getNotifyTime(context).split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(SharedUtil.getNotifyTime(context).split(":")[1]));
        setAlarm(context, calendar);
    }

    private static void setAlarm(Context context, Calendar calendar) {

        alarmMgr = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                android.app.AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
