package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CutOffDepartments;

import java.util.List;

@Dao
public interface CutOffDepartmentsDAO {

    @Insert
    void insert(CutOffDepartments cutOffDepartments);

    @Update
    void update(CutOffDepartments cutOffDepartments);

    @Query("SELECT * FROM cutoffdepartments WHERE cutOffId = :cutOffId")
    List<CutOffDepartments> fetchByCutOffId(int cutOffId);

    @Query("SELECT * FROM cutOffDepartments WHERE isSentToServer = 0")
    LiveData<List<CutOffDepartments>> fetchCompleteCutOffDepartmentsToSync();

    @Query("UPDATE cutOffDepartments SET isSentToServer = 1 WHERE id = :cutOffDepartmentId")
    void updateCutOffDepartmentsSentToServer(int cutOffDepartmentId);

}
