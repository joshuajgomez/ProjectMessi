package com.josh.gomez.projectmessi.module.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import androidx.annotation.Nullable;


import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;
import com.josh.gomez.projectmessi.service.alarmReceiver.AlarmManager;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        bindPreferenceSummaryToValue(findPreference("time"));
        bindPreferenceSummaryToValue(findPreference(Constants.NOTIFICATION_FLAG));
        Preference preference = findPreference("version_name");
        preference.setSummary(ResUtil.getAppVersionName());
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        if (preference instanceof SwitchPreference) {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), true));
        } else if (preference instanceof TimePreference) {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), SharedUtil.getNotifyTime()));
        }
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof SwitchPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                // Set the summary to reflect the new value.

            }
            if (preference instanceof TimePreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                // Set the summary to reflect the new value.
                preference.setSummary(getTime(stringValue));
                AlarmManager.initAlarmManager(Constants.context, stringValue);
            }
            return true;
        }
    };

    private static String getTime(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int minutes = Integer.parseInt(time.split(":")[1]);
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "pm";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "am";
        } else if (hour == 12) {
            timeSet = "pm";
        } else {
            timeSet = "am";
        }

        String min = "";
        if (minutes < 10)
            min = "0" + minutes;
        else
            min = String.valueOf(minutes);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min).append(" ").append(timeSet).toString();
        return aTime;
    }
}
