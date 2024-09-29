package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.MachineDetails;

@Dao
public interface MachineDetailsDAO {

    @Insert
    void insert(MachineDetails machineDetails);

    @Update
    Void update(MachineDetails machineDetails);

    @Query("SELECT * FROM machineDetails LIMIT 1")
    MachineDetails fetchMachineDetails();

    @Delete
    void remove(MachineDetails machineDetails);

    @Query("SELECT * FROM machineDetails WHERE isSentToServer = 0 LIMIT 1")
    LiveData<MachineDetails> fetchMachineDetailsToSync();

    @Query("SELECT * FROM machineDetails WHERE isSentToServer = 0 LIMIT 1")
    MachineDetails fetchMachineDetailsToUpload();

    @Query("UPDATE machineDetails SET isSentToServer = 1 WHERE id = :machineId")
    void updateMachineDetailsSentToServer(int machineId);

}
