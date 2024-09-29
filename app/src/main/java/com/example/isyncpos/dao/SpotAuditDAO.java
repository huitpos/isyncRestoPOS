package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.SpotAudit;

import java.util.List;

@Dao
public interface SpotAuditDAO {

    @Insert
    Long insert(SpotAudit spotAudit);

    @Update
    void update(SpotAudit spotAudit);

    @Query("SELECT * FROM spotAudit WHERE id = :id")
    SpotAudit fetchById(int id);

    @Query("SELECT * FROM spotAudit WHERE isCutOff = 0")
    List<SpotAudit> fetchSpotAuditToCutOff();

    @Query("SELECT * FROM spotAudit WHERE isSentToServer = 0")
    LiveData<List<SpotAudit>> fetchCompleteSpotAuditToSync();

    @Query("UPDATE spotAudit SET isSentToServer = 1 WHERE id = :spotAuditId")
    void updateSpotAuditSentToServer(int spotAuditId);

}
