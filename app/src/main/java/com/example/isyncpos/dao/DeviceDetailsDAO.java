package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.DeviceDetails;

@Dao
public interface DeviceDetailsDAO {

    @Insert
    void insert(DeviceDetails deviceDetails);

    @Update
    void update(DeviceDetails deviceDetails);

    @Query("SELECT * FROM deviceDetails LIMIT 1")
    DeviceDetails fetch();

}
