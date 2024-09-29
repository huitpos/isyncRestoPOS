package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.EndOfDay;

import java.util.List;

@Dao
public interface EndOfDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EndOfDay endOfDay);

    @Update
    void update(EndOfDay endOfDay);

    @Query("SELECT * FROM endOfDay WHERE id = :endOfDayId")
    EndOfDay fetchEndOfDayInformationById(int endOfDayId);

    @Query("SELECT * FROM endOfDay WHERE DATE(treg) = :date")
    EndOfDay fetchEndOfDayNow(String date);

    @Query("SELECT * FROM endOfDay WHERE isSentToServer = 0")
    LiveData<List<EndOfDay>> fetchCompleteEndOfDayToSync();

    @Query("SELECT * FROM endOfDay WHERE isSentToServer = 0")
    List<EndOfDay> fetchCompleteEndOfDayToUpload();

    @Query("UPDATE endOfDay SET isSentToServer = 1 WHERE id = :endOfDayId")
    void updateEndOfDaySentToServer(int endOfDayId);

    //WHERE DATE(treg) = STRFTIME('%Y-%m-%d', 'now')
    @Query("SELECT * FROM endOfDay ORDER BY readingNumber ASC")
    LiveData<List<EndOfDay>> fetchEndOfDayForTodayToReprint();

}
