package com.josh.gomez.josh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class main {

    public static void main(String args[]) {
        System.out.println("daysOfMonth " +getTotalDaysInMonth(2));
    }

    public static int getTotalDaysInMonth(int month) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
