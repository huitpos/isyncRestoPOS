package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.Transactions;

import java.util.List;

@Dao
public interface CutOffDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CutOff cutOff);

    @Update
    void update(CutOff cutOff);

    @Query("SELECT * FROM cutOff WHERE endOfDayId = 0 ORDER BY beginningOR ASC")
    List<CutOff> fetchCutOffToEndOfDay();

    @Query("SELECT * FROM cutOff ORDER BY endingOR DESC LIMIT 1")
    CutOff fetchLastCutOffInformation();

    @Query("SELECT * FROM cutOff WHERE endOfDayId != 0 ORDER BY endingOR DESC LIMIT 1")
    CutOff fetchLastCutOffForEndOfDayInformation();

    @Query("SELECT * FROM cutOff WHERE id = :cutOffId")
    CutOff fetchCutOffInformationById(int cutOffId);

    @Query("SELECT * FROM cutOff WHERE isSentToServer = 0")
    LiveData<List<CutOff>> fetchCompleteCutOffToSync();

    @Query("SELECT * FROM cutOff WHERE isSentToServer = 0")
    List<CutOff> fetchCompleteCutOffToUpload();

    @Query("UPDATE cutOff SET isSentToServer = 1 WHERE id = :cutOffId")
    void updateCutOffSentToServer(int cutOffId);

    @Query("SELECT DATE(treg) AS 'today' FROM cutOff WHERE endOfDayId = 0 AND DATE(treg) != STRFTIME('%Y-%m-%d', 'now') GROUP BY DATE(treg) ORDER BY DATE(treg) DESC")
    List<String> checkForEndOfDayProcess();

    @Query("SELECT * FROM cutOff WHERE endOfDayId = 0 AND DATE(treg) != STRFTIME('%Y-%m-%d', 'now') AND DATE(treg) = :today ORDER BY beginningOR ASC")
    List<CutOff> fetchCutOffToEndOfDayByDate(String today);

    //WHERE DATE(treg) = STRFTIME('%Y-%m-%d', 'now')
    @Query("SELECT * FROM cutOff ORDER BY readingNumber ASC")
    LiveData<List<CutOff>> fetchCutOffForTodayToReprint();

}
