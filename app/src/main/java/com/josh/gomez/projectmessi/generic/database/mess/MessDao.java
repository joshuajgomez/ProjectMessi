package com.josh.gomez.projectmessi.generic.database.mess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by bayasys on 13/01/2018.
 */
@Dao
public interface MessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMess(Mess Mess);

    @Query("SELECT * FROM Mess ORDER BY date")
    List<Mess> getAll();

    @Query("SELECT * FROM Mess WHERE date = :date")
    List<Mess> getMessByDate(String date);

    @Query("DELETE FROM Mess")
    void clearMess();

    @Query("DELETE FROM Mess WHERE date = :date")
    void deleteMess(String date);
}
