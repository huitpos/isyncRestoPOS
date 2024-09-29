package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Payments;

import java.util.List;

@Dao
public interface PaymentsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Payments payments);

    @Update
    void update(Payments payments);

    @Delete
    void remove(Payments payments);

    @Query("SELECT SUM(amount) AS 'totalPayment' FROM payments WHERE transactionId = :transactionId AND isVoid = 0")
    Double fetchTransactionPaymentsSum(int transactionId);

    @Query("SELECT SUM(amount) AS 'totalPayment' FROM payments WHERE transactionId = :transactionId AND isVoid = 0 AND isAccountReceivable = 0")
    Double fetchARTransactionPaymentsSum(int transactionId);

    @Query("SELECT * FROM payments WHERE transactionId = :transactionId AND isVoid = 0")
    List<Payments> fetchTransactionPayments(int transactionId);

    @Query("SELECT * FROM payments WHERE transactionId = :transactionId AND isVoid = 0 AND isAccountReceivable = 0")
    List<Payments> fetchARTransactionPayments(int transactionId);

    @Query("SELECT SUM(amount) AS 'totalPayment' FROM payments WHERE transactionId = :transactionId AND paymentTypeName = 'Cash' AND isVoid = 0")
    Double fetchTransactionPaymentCashSum(int transactionId);

    @Query("SELECT * FROM payments WHERE isSentToServer = 0")
    LiveData<List<Payments>> fetchCompletePaymentToSync();

    @Query("SELECT * FROM payments WHERE id = :paymentId")
    Payments fetchPaymentById(int paymentId);

    @Query("SELECT SUM(amount) AS 'totalPayment' FROM payments WHERE transactionId = :transactionId AND paymentTypeName != 'Cash' AND isVoid = 0")
    Double fetchTransactionOthersSum(int transactionId);

    @Query("SELECT SUM(amount) AS 'totalPayment' FROM payments WHERE transactionId = :transactionId AND paymentTypeName != 'Cash' AND isVoid = 0 AND isAccountReceivable = 0")
    Double fetchARTransactionOthersSum(int transactionId);

    @Query("SELECT * FROM payments WHERE isSentToServer = 0")
    List<Payments> fetchCompletePaymentToUpload();

    @Query("UPDATE payments SET isSentToServer = 1 WHERE id = :paymentId")
    void updatePaymentSentToServer(int paymentId);

    @Query("SELECT * FROM payments WHERE transactionId = :transactionId AND isAccountReceivable = 1 AND isVoid = 0")
    List<Payments> fetchOtherPaymentsForAR(int transactionId);

    @Query("SELECT * FROM payments WHERE transactionId = :transactionId AND isAccountReceivable = 0 AND isVoid = 0")
    List<Payments> fetchOtherPaymentsForNotAR(int transactionId);

}
