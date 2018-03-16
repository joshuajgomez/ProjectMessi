package com.josh.gomez.projectmessi.generic.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Joshua Gomez on 12/12/2017.
 */

public class Animation {


    public static void SlideInDown(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(400)
                .playOn(view);
    }

    public static void SlideOutUp(View view) {
        YoYo.with(Techniques.SlideOutUp)
                .duration(400)
                .playOn(view);
    }

    public static void FadeInDown(View view) {
        YoYo.with(Techniques.FadeInDown)
                .duration(400)
                .playOn(view);
    }

    public static void FadeInUp(View view) {
        YoYo.with(Techniques.FadeInUp)
                .duration(400)
                .playOn(view);

    }

    public static void FadeOutUp(View view) {
        YoYo.with(Techniques.FadeOutUp)
                .duration(400)
                .playOn(view);
    }

    public static void SlideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public static void SlideDown(View view, int height) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                height); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

}
