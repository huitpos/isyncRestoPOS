package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOffDiscounts;

import java.util.List;

@Dao
public interface CutOffDiscountsDAO {

    @Insert
    void insert(CutOffDiscounts cutOffDiscounts);

    @Update
    void update(CutOffDiscounts cutOffDiscounts);

    @Query("SELECT * FROM cutOffDiscounts WHERE cutOffId = :cutOffId")
    List<CutOffDiscounts> fetchByCutOffId(int cutOffId);

    @Query("SELECT * FROM cutOffDiscounts WHERE isSentToServer = 0")
    LiveData<List<CutOffDiscounts>> fetchCompleteCutOffDiscountsToSync();

    @Query("UPDATE cutOffDiscounts SET isSentToServer = 1 WHERE id = :cutOffDiscountId")
    void updateCutOffDiscountsSentToServer(int cutOffDiscountId);


}
