package com.josh.gomez.projectmessi.generic.database.mess;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * Created by Joshua Gomez on 13/01/2018.
 */

@Entity
public class Mess {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String date;
    public boolean breakfast = false;
    public boolean lunch = false;
    public boolean dinner = false;

}
