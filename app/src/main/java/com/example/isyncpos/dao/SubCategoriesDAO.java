package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.SubCategories;

@Dao
public interface SubCategoriesDAO {

    @Insert
    void insert(SubCategories subCategories);

    @Update
    void update(SubCategories subCategories);

    @Query("SELECT * FROM subCategories WHERE coreId = :subCategoryId")
    SubCategories fetchSubCategory(int subCategoryId);

}
