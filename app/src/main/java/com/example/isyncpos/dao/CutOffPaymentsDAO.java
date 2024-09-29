package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOffPayments;

import java.util.List;

@Dao
public interface CutOffPaymentsDAO {

    @Insert
    void insert(CutOffPayments cutOffPayments);

    @Update
    void update(CutOffPayments cutOffPayments);

    @Query("SELECT * FROM cutOffPayments WHERE cutOffId = :cutOffId")
    List<CutOffPayments> fetchByCutOffId(int cutOffId);

    @Query("SELECT * FROM cutOffPayments WHERE isSentToServer = 0")
    LiveData<List<CutOffPayments>> fetchCompleteCutOffPaymentsToSync();

    @Query("UPDATE cutOffPayments SET isSentToServer = 1 WHERE id = :cutOffPaymentId")
    void updateCutOffPaymentsSentToServer(int cutOffPaymentId);

}
