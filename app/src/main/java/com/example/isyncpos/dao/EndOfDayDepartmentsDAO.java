package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.EndOfDayDepartments;

import java.util.List;

@Dao
public interface EndOfDayDepartmentsDAO {

    @Insert
    void insert(EndOfDayDepartments endOfDayDepartments);

    @Query("SELECT * FROM endOfDayDepartments WHERE isSentToServer = 0")
    LiveData<List<EndOfDayDepartments>> fetchCompleteEndOfDayDepartmentsToSync();

    @Query("UPDATE endOfDayDepartments SET isSentToServer = 1 WHERE id = :endOfDayDepartmentId")
    void updateEndOfDayDepartmentsSentToServer(int endOfDayDepartmentId);

}
