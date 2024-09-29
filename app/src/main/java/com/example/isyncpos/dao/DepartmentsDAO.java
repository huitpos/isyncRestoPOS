package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Departments;

import java.util.List;

@Dao
public interface DepartmentsDAO {

    @Insert
    void insert(Departments departments);

    @Update
    void update(Departments departments);

    @Query("SELECT * FROM departments WHERE status = 1 ORDER BY name ASC")
    LiveData<List<Departments>> fetchAll();

    @Query("SELECT * FROM departments WHERE coreId = :departmentId")
    Departments fetchDepartment(int departmentId);

    @Query("SELECT * FROM departments ORDER BY name ASC")
    List<Departments> fetchAllDepartmentInformation();

}
