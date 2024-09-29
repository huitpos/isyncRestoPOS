package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.PaymentTypes;

import java.util.List;

@Dao
public interface PaymentTypesDAO {

    @Insert
    void insert(PaymentTypes paymentTypes);

    @Update
    void update(PaymentTypes paymentTypes);

    @Query("SELECT * FROM paymentTypes WHERE coreId != 1 AND status = 1")
    LiveData<List<PaymentTypes>> fetchAll();

    @Query("SELECT * FROM paymentTypes WHERE coreId = :id")
    PaymentTypes fetchById(int id);

    @Query("SELECT * FROM paymentTypes")
    List<PaymentTypes> fetchAllPaymentType();

    @Query("SELECT * FROM paymentTypes WHERE coreId != 1 AND status = 1 AND isAR = 0")
    LiveData<List<PaymentTypes>> fetchAllWithOutAR();

}
