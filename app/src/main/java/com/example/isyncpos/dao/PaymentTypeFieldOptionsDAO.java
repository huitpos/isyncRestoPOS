package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.PaymentTypeFieldOptions;

import java.util.List;

@Dao
public interface PaymentTypeFieldOptionsDAO {

    @Insert
    void insert(PaymentTypeFieldOptions paymentTypeFieldOptions);

    @Query("SELECT * FROM  paymentTypeFieldOptions WHERE paymentTypeFieldId = :paymentTypeFieldId")
    List<PaymentTypeFieldOptions> fetchByPaymentTypeFieldId(int paymentTypeFieldId);

    @Query("DELETE FROM paymentTypeFieldOptions WHERE coreId = :paymentTypeId")
    Void delete(int paymentTypeId);

}
