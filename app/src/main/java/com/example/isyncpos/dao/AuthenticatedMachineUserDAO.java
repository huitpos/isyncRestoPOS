package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.AuthenticatedMachineUser;

import java.util.List;

@Dao
public interface AuthenticatedMachineUserDAO {

    @Insert
    Long insert(AuthenticatedMachineUser authenticatedMachineUser);

    @Update
    void update(AuthenticatedMachineUser authenticatedMachineUser);

    @Query("SELECT * FROM authenticatedMachineUser LIMIT 1")
    AuthenticatedMachineUser fetch();

}
