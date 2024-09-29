package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.DiscountTypeFieldOptions;

import java.util.List;

@Dao
public interface DiscountTypeFieldOptionsDAO {

    @Insert
    void insert(DiscountTypeFieldOptions discountTypeFieldOptions);

    @Query("DELETE FROM discountTypeFieldOptions WHERE coreId = :discountTypeId")
    Void delete(int discountTypeId);

    @Query("SELECT * FROM discountTypeFieldOptions WHERE discountTypeFieldId = :discountTypeFieldId")
    List<DiscountTypeFieldOptions> fetchByDiscountTypeFieldId(int discountTypeFieldId);

}
