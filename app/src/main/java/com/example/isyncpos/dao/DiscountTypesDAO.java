package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.DiscountTypes;

import java.util.List;

@Dao
public interface DiscountTypesDAO {

    @Insert
    void insert(DiscountTypes discountTypes);

    @Update
    void update(DiscountTypes discountTypes);

    @Query("SELECT * FROM discountTypes WHERE status = 1")
    LiveData<List<DiscountTypes>> fetchAll();

    @Query("SELECT * FROM discountTypes WHERE coreId = :coreId")
    DiscountTypes fetchById(int coreId);

    @Query("SELECT * FROM discountTypes ORDER BY name ASC")
    List<DiscountTypes> fetchAllDiscountTypeInformation();

}
