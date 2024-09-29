package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOffProducts;

import java.util.List;

@Dao
public interface CutOffProductsDAO {

    @Insert
    void insert(CutOffProducts cutOffProducts);

    @Update
    void update(CutOffProducts cutOffProducts);

    @Query("SELECT * FROM cutOffProducts WHERE endOfDayId = 0")
    List<CutOffProducts> fetchCutOffProductsToEndOfDay();

    @Query("SELECT * FROM cutOffProducts WHERE isSentToServer = 0")
    LiveData<List<CutOffProducts>> fetchCompleteCutOffProductsToSync();

    @Query("UPDATE cutOffProducts SET isSentToServer = 1 WHERE id = :cutOffProductId")
    void updateCutOffProductsSentToServer(int cutOffProductId);

}
