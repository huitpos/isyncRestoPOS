package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.isyncpos.entity.Payouts;

@Dao
public interface PayoutsDAO {

    @Insert
    void insert(Payouts payouts);

}
