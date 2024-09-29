package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.isyncpos.entity.AuditTrails;

import java.util.List;

@Dao
public interface AuditTrailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AuditTrails auditTrails);

    @Query("SELECT * FROM auditTrails WHERE isSentToServer = 0")
    LiveData<List<AuditTrails>> fetchCompleteAuditTrailsToSync();

    @Query("UPDATE auditTrails SET isSentToServer = 1 WHERE id = :auditTrailId")
    void updateAuditTrailSentToServer(int auditTrailId);

}
