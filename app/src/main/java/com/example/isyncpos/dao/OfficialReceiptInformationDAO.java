package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.OfficialReceiptInformation;

import java.util.List;

@Dao
public interface OfficialReceiptInformationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OfficialReceiptInformation officialReceiptInformation);

    @Update
    void update(OfficialReceiptInformation officialReceiptInformation);

    @Query("SELECT * FROM officialReceiptInformation WHERE transactionId = :transactionId")
    OfficialReceiptInformation fetchByTransactionId(int transactionId);

    @Query("SELECT * FROM officialReceiptInformation WHERE isSentToServer = 0")
    LiveData<List<OfficialReceiptInformation>> fetchCompleteOfficialReceiptInformationToSync();

    @Query("UPDATE officialReceiptInformation SET isSentToServer = 1 WHERE id = :officialReceiptId")
    void updateOfficialReceiptInformationSentToServer(int officialReceiptId);

}
