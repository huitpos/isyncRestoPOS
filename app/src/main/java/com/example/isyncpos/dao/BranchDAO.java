package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Branch;

@Dao
public interface BranchDAO {

    @Insert
    void insert(Branch branch);

    @Update
    void update(Branch branch);

    @Query("SELECT * FROM branch LIMIT 1")
    Branch fetch();

    @Delete
    void remove(Branch branch);

    @Query("SELECT * FROM branch LIMIT 1")
    LiveData<Branch> fetchBranchInformation();

}
