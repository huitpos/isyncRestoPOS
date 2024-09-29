package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.EndOfDayPayments;

import java.util.List;

@Dao
public interface EndOfDayPaymentsDAO {

    @Insert
    void insert(EndOfDayPayments endOfDayPayments);

    @Query("SELECT * FROM endOfDayPayments WHERE isSentToServer = 0")
    LiveData<List<EndOfDayPayments>> fetchCompleteEndOfDayPaymentsToSync();

    @Query("UPDATE endOfDayPayments SET isSentToServer = 1 WHERE id = :endOfDayPaymentId")
    void updateEndOfDayPaymentsSentToServer(int endOfDayPaymentId);

}
