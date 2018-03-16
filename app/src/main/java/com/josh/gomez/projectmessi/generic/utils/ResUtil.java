package com.josh.gomez.projectmessi.generic.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.josh.gomez.projectmessi.BuildConfig;
import com.josh.gomez.projectmessi.generic.repository.Constants;

/**
 * Created by bayasys on 26/01/2018.
 */

public class ResUtil {

    public static Drawable getMipmap(String ImageName) {
        Context c = Constants.context;
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "mipmap", c.getPackageName()));
    }

    public static Drawable getDrawable(String ImageName) {
        Context c = Constants.context;
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    public static String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }
}
