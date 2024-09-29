package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.Roles;

import java.util.List;

@Dao
public interface RolesDAO {

    @Insert
    void insert(Roles roles);

    @Query("SELECT * FROM roles WHERE userId = :userId")
    List<Roles> fetchByUserId(int userId);

    @Query("DELETE FROM roles")
    Void removeRoles();


}
