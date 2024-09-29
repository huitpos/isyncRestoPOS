package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.Upload;

import java.util.List;

@Dao
public interface UploadDAO {

    @Insert
    void insert(Upload upload);

    @Query("SELECT * FROM upload")
    List<Upload> fetchAll();

}
