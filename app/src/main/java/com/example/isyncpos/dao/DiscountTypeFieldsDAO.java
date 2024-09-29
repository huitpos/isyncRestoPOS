package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.DiscountTypeFields;

import java.util.List;

@Dao
public interface DiscountTypeFieldsDAO {

    @Insert
    void insert(DiscountTypeFields discountTypeFields);

    @Query("DELETE FROM discountTypeFields WHERE discountTypeId = :discountTypeId")
    Void delete(int discountTypeId);

    @Query("SELECT * FROM discountTypeFields WHERE discountTypeId = :discountTypeId")
    List<DiscountTypeFields> fetchByDiscountTypeId(int discountTypeId);

}
