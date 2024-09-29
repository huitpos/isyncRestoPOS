package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.isyncpos.entity.TableStatus;

@Dao
public interface TableStatusDAO {

    @Insert
    void insert(TableStatus tableStatus);

}
