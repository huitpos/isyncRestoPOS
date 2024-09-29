package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.isyncpos.entity.TableTypes;

@Dao
public interface TableTypesDAO {

    @Insert
    void insert(TableTypes tableTypes);

}
