package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CashDenomination;

import java.util.List;

@Dao
public interface CashDenominationDAO {

    @Insert
    void insert(CashDenomination cashDenomination);

    @Update
    void update(CashDenomination cashDenomination);

    @Query("SELECT * FROM cashDenomination ORDER BY amount DESC")
    LiveData<List<CashDenomination>> fetchAll();

    @Query("SELECT * FROM cashDenomination WHERE coreId = :id")
    CashDenomination fetchById(int id);

}
