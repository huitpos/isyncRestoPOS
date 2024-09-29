package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.EndOfDayDiscounts;

import java.util.List;

@Dao
public interface EndOfDayDiscountsDAO {

    @Insert
    void insert(EndOfDayDiscounts endOfDayDiscounts);

    @Query("SELECT * FROM endOfDayDiscounts WHERE isSentToServer = 0")
    LiveData<List<EndOfDayDiscounts>> fetchCompleteEndOfDayDiscountsToSync();

    @Query("UPDATE endOfDayDiscounts SET isSentToServer = 1 WHERE id = :endOfDayDiscountId")
    void updateEndOfDayDiscountsSentToServer(int endOfDayDiscountId);

}
