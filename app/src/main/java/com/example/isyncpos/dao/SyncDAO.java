package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Sync;

import java.util.List;

@Dao
public interface SyncDAO {

    @Insert
    void insert(Sync sync);

    @Update
    void update(Sync sync);

    @Query("SELECT * FROM sync")
    LiveData<List<Sync>> fetchAll();

    @Query("SELECT * FROM sync WHERE isSync = 0")
    List<Sync> fetchUnfinish();

    @Query("SELECT * FROM sync WHERE id = :id")
    Sync fetchById(int id);

    @Query("SELECT * FROM sync")
    List<Sync> fetchSyncList();

}
