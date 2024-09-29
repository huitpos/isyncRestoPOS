package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.PaymentOtherInformations;

import java.util.List;

@Dao
public interface PaymentOtherInformationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PaymentOtherInformations paymentOtherInformations);

    @Update
    void update(PaymentOtherInformations paymentOtherInformations);

    @Query("SELECT * FROM paymentOtherInformations WHERE paymentId = :paymentId ORDER BY id DESC")
    List<PaymentOtherInformations> fetchByPaymentId(int paymentId);

    @Query("SELECT * FROM paymentOtherInformations WHERE isSentToServer = 0")
    LiveData<List<PaymentOtherInformations>> fetchCompletePaymentOtherInformationsToSync();

    @Query("SELECT * FROM paymentOtherInformations WHERE isCutOff = 0")
    List<PaymentOtherInformations> fetchPaymentOtherInformationToCutOff();

    @Query("UPDATE paymentOtherInformations SET isSentToServer = 1 WHERE id = :paymentOtherInformationId")
    void updatePaymentOtherInformationSentToServer(int paymentOtherInformationId);

    @Query("SELECT * FROM paymentOtherInformations WHERE transactionId = :transactionId")
    List<PaymentOtherInformations> fetchPaymentOtherInformationByTransactionId(int transactionId);

}
