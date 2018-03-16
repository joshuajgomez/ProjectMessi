package com.josh.gomez.projectmessi.generic.utils;

/**
 * Created by Joshua Gomez on 26/01/2018.
 */

public class MathUtil {

    public static int getPercent(int value, int total) {
        float value1 = value;
        float total1 = total;
        float percent = value1 / total1 * 100;
        return (int) percent;
    }

}
