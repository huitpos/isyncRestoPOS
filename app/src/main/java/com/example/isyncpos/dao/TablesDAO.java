package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.isyncpos.entity.Tables;

@Dao
public interface TablesDAO {

    @Insert
    void insert(Tables tables);

}
