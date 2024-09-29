package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.Transactions;

import java.util.List;

@Dao
public interface SafekeepingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Safekeeping safekeeping);

    @Update
    void update(Safekeeping safekeeping);

    @Query("SELECT * FROM safekeeping WHERE id = :safekeepingId")
    Safekeeping fetchById(int safekeepingId);

    @Query("SELECT SUM(amount - shortOver) AS 'sumSafekeepingAmount' FROM safekeeping WHERE isCutOff = 0")
    Double sumAllSafekeeping();

    @Query("SELECT * FROM safekeeping WHERE isCutOff = 0 ORDER BY id ASC")
    List<Safekeeping> fetchSafekeepingToCutOff();

    @Query("SELECT * FROM safekeeping WHERE isSentToServer = 0")
    LiveData<List<Safekeeping>> fetchCompleteSafekeepingToSync();

    @Query("UPDATE safekeeping SET isSentToServer = 1 WHERE id = :safeKeepingId")
    void updateSafekeepingSentToServer(int safeKeepingId);

    @Query("SELECT * FROM safekeeping WHERE isCutOff = 1 AND endOfDayId = 0")
    List<Safekeeping> fetchSafekeepingToEndOfDay();

}
