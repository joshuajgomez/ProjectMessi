package com.josh.gomez.projectmessi.module.report.reportDetail;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 16/01/2018.
 */

public class reportDetailModel {

    public int totalRate = 0;
    public int totalBreakfastRate = 0;
    public int totalLunchRate = 0;
    public int totalDinnerRate = 0;
    public int totalBreakfastCount = 0;
    public int totalLunchCount = 0;
    public int totalDinnerCount = 0;
    public List<Mess> messList = new ArrayList<>();
    public int month;
    public int year;

}
