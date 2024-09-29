package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Devices;

import java.util.List;

@Dao
public interface DevicesDAO {

    @Insert
    void insert(Devices devices);

    @Update
    void update(Devices devices);

    @Query("SELECT * FROM devices")
    LiveData<List<Devices>> fetchAll();

    @Query("SELECT * FROM devices WHERE serialNumber = :serialNumber")
    Devices fetchDeviceSerialNumber(String serialNumber);

    @Query("DELETE FROM devices WHERE id = :id")
    void removeDevice(int id);

    @Query("SELECT * FROM devices WHERE name = :name OR serialNumber = :serialNumber")
    Devices validateDevice(String name, String serialNumber);

    @Query("SELECT * FROM devices WHERE id = :deviceId")
    Devices fetchDeviceID(int deviceId);

}
