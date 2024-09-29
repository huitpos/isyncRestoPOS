package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;

import java.util.List;

@Dao
public interface SafekeepingDenominationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SafekeepingDenomination safekeepingDenomination);

    @Update
    void update(SafekeepingDenomination safekeepingDenomination);

    @Query("SELECT * FROM safekeepingDenomination WHERE safekeepingId = :safekeepingId ORDER BY amount DESC")
    List<SafekeepingDenomination> fetchSafekeepingDenominationBySafekeepingId(int safekeepingId);

    @Query("SELECT * FROM safekeepingDenomination WHERE isSentToServer = 0")
    LiveData<List<SafekeepingDenomination>> fetchCompleteSafekeepingDenominationToSync();

    @Query("UPDATE safekeepingDenomination SET isSentToServer = 1 WHERE id = :safekeepingDenominationId")
    void updateSafekeepingDenominationSentToServer(int safekeepingDenominationId);

}
