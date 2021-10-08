package com.josh.gomez.projectmessi.service.alarmReceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;
import com.josh.gomez.projectmessi.module.messEntry.MessEntryView;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent notifyPendingIntent;
    static int NOTIFICATION_ID = 123456789;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Constants.context = context;
        int hour = Integer.parseInt(SharedUtil.getNotifyTime(context).split(":")[0]);
        int minute = Integer.parseInt(SharedUtil.getNotifyTime(context).split(":")[1]);
        if (DateUtil.getCurrentHour() == hour
                && (DateUtil.getCurrentMinute() == minute || DateUtil.getCurrentMinute() + 1 == minute)
                && SharedUtil.getNotifyFlag(context))
            showNotification(context);
    }

    public void showNotification(Context context) {

        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Mess-mate");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }


        Intent notifyIntent;
        notifyIntent = new Intent(context, MessEntryView.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        requestID,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.dinner)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                .setContentText("Tap to update today's Mess");
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
