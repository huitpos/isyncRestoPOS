package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.PaymentTypeFields;

import java.util.List;

@Dao
public interface PaymentTypeFieldsDAO {

    @Insert
    void insert(PaymentTypeFields paymentTypeFields);

    @Query("SELECT * FROM paymentTypeFields WHERE paymentTypeId = :paymentTypeId ORDER BY coreId ASC")
    List<PaymentTypeFields> fetchByPaymentTypeId(int paymentTypeId);

    @Query("DELETE FROM paymentTypeFields WHERE paymentTypeId = :paymentTypeId")
    Void delete(int paymentTypeId);

}
