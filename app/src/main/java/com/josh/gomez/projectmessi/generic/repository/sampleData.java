package com.josh.gomez.projectmessi.generic.repository;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 13/01/2018.
 */

public class sampleData {

    public static List<Mess> getMessList() {
        List<Mess> messList = new ArrayList<>();
        int noOfDays = DateUtil.getTotalDaysInCurrentMonth();
        for (int i = 0; i < noOfDays; i++) {
            Mess Mess = new Mess();
            Mess.breakfast = true;
            Mess.lunch = true;
            Mess.dinner = true;
            Mess.date = i + "/01/2018";
            messList.add(Mess);
        }
        return messList;
    }

}
