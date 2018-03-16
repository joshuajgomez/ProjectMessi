package com.josh.gomez.projectmessi.generic.database.mess;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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
