package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.SpotAuditDenomination;

import java.util.List;

@Dao
public interface SpotAuditDenominationDAO {

    @Insert
    void insert(SpotAuditDenomination spotAuditDenomination);

    @Query("SELECT * FROM spotAuditDenomination WHERE spotAuditId = :spotAuditId")
    List<SpotAuditDenomination> fetchBySpotAuditId(int spotAuditId);

    @Query("SELECT * FROM spotAuditDenomination WHERE isSentToServer = 0")
    LiveData<List<SpotAuditDenomination>> fetchCompleteSpotAuditDenominationToSync();

    @Query("UPDATE spotAuditDenomination SET isSentToServer = 1 WHERE id = :spotAuditDenominationId")
    void updateSpotAuditDenominationSentToServer(int spotAuditDenominationId);

}
