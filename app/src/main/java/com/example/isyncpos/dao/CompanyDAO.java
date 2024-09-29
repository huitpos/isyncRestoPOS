package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Company;

@Dao
public interface CompanyDAO {

    @Insert
    void insert(Company company);

    @Update
    void update(Company company);

    @Query("SELECT * FROM company LIMIT 1")
    Company fetch();

    @Delete
    void remove(Company company);

    @Query("SELECT * FROM company LIMIT 1")
    LiveData<Company> fetchCompanyInformation();

}
