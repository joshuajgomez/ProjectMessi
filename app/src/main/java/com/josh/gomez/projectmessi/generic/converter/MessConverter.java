package com.josh.gomez.projectmessi.generic.converter;

import com.josh.gomez.projectmessi.generic.database.mess.Mess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Gomez on 22/01/2018.
 */

public class MessConverter {

    public static final String DATE_KEY = "DATE_KEY";
    public static final String BREAKFAST_KEY = "BREAKFAST_KEY";
    public static final String LUNCH_KEY = "LUNCH_KEY";
    public static final String DINNER_KEY = "DINNER_KEY";

    public static JSONArray convert(List<Mess> messList) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < messList.size(); i++) {
            JSONObject object = new JSONObject();
            Mess mess = messList.get(i);
            try {
                object.put(DATE_KEY, mess.date);
                object.put(BREAKFAST_KEY, mess.breakfast);
                object.put(LUNCH_KEY, mess.lunch);
                object.put(DINNER_KEY, mess.dinner);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }


    public static List<Mess> convert(JSONArray array) {
        List<Mess> messList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Mess mess = new Mess();
            try {
                JSONObject object = array.getJSONObject(i);
                mess.date = object.getString(DATE_KEY);
                mess.breakfast = object.getBoolean(BREAKFAST_KEY);
                mess.lunch = object.getBoolean(LUNCH_KEY);
                mess.dinner = object.getBoolean(DINNER_KEY);
                messList.add(mess);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messList;
    }

}
