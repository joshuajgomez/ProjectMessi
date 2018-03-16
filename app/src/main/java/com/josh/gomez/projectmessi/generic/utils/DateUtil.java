package com.josh.gomez.projectmessi.generic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by bayasys on 13/01/2018.
 */

public class DateUtil {

    public static String getCurrentDate() {
        Calendar mCalendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        return sdf.format(mCalendar.getTime());
    }

    public static String getCurrentDateFormatted() {
        Calendar mCalendar = Calendar.getInstance();
        String myFormat = "dd_MM_yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        return sdf.format(mCalendar.getTime());
    }

    public static String getDayNumber(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        try {
            return sdf.format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDayWeek(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        try {
            return sdf.format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getMonthYear(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        try {
            return sdf.format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getShortDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        try {
            return sdf.format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getTotalDaysInCurrentMonth() {
        Calendar c = new GregorianCalendar();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getTotalDaysInMonth(int month) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        try {
            return Integer.parseInt(sdf.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("m");
        try {
            return Integer.parseInt(sdf.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMonthFromDate(String date) {
        return Integer.parseInt(date.split("/")[1]);
    }

    public static int getYearFromDate(String date) {
        return Integer.parseInt(date.split("/")[2]);
    }

    public static String getMonthWord(int month) {
        switch (month) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }
        return "";
    }
}
