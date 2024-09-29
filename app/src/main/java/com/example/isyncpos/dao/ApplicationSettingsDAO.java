package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.ApplicationSettings;

@Dao
public interface ApplicationSettingsDAO {

    @Insert
    void insert(ApplicationSettings applicationSettings);

    @Update
    void update(ApplicationSettings applicationSettings);

    @Query("SELECT * FROM applicationSettings LIMIT 1")
    ApplicationSettings fetch();

}
