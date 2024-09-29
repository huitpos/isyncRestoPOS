package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.ChargeAccount;

import java.util.List;

@Dao
public interface ChargeAccountDAO {

    @Insert
    void insert(ChargeAccount chargeAccount);

    @Update
    void update(ChargeAccount chargeAccount);

    @Query("SELECT * FROM chargeAccount WHERE id = :id")
    ChargeAccount fetchById(int id);

    @Query("SELECT * FROM chargeAccount")
    List<ChargeAccount> fetchAll();

}
