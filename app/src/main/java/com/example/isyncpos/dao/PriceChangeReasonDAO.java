package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.PriceChangeReason;

import java.util.List;

@Dao
public interface PriceChangeReasonDAO {

    @Insert
    void insert(PriceChangeReason priceChangeReason);

    @Update
    void update(PriceChangeReason priceChangeReason);

    @Query("SELECT * FROM priceChangeReason WHERE coreId = :coreId")
    PriceChangeReason fetchByCoreId(int coreId);

    @Query("SELECT * FROM priceChangeReason WHERE status = 1 ORDER BY name ASC")
    List<PriceChangeReason> fetchAll();

}
