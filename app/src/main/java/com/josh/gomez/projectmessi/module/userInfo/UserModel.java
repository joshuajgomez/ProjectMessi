package com.josh.gomez.projectmessi.module.userInfo;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;

import java.util.List;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class UserModel {

    public String userName = "Mess Eater";
    public int totalRate = 0;
    public int totalBreakfastRate = 0;
    public int totalLunchRate = 0;
    public int totalDinnerRate = 0;
    public int totalMealCount = 0;
    public int totalBreakfastCount = 0;
    public int totalLunchCount = 0;
    public int totalDinnerCount = 0;
    public List<Mess> messList;
    public int month;
    public int year;

}
