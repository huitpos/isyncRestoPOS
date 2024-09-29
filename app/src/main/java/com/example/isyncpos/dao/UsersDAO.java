package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Users;

import java.util.List;

@Dao
public interface UsersDAO {

    @Insert
    void insert(Users users);

    @Update
    void update(Users users);

    @Query("SELECT * FROM users")
    List<Users> fetchAll();

    @Query("SELECT * FROM users WHERE username = :username OR email = :username")
    Users findByUsernameOrEmail(String username);

    @Query("SELECT * FROM users WHERE username = :credential OR email = :credential")
    Users authenticate(String credential);

    @Query("SELECT * FROM users WHERE coreId = :id")
    Users fetchById(int id);

}
