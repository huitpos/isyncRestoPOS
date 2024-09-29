package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Categories;

@Dao
public interface CategoriesDAO {

    @Insert
    void insert(Categories categories);

    @Update
    void update(Categories categories);

    @Query("SELECT * FROM categories WHERE coreId = :categoryId")
    Categories fetchCategory(int categoryId);

}
