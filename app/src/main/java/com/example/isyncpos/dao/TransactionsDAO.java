package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Transactions;

import java.util.List;

@Dao
public interface TransactionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Transactions transactions);

    @Update
    Void update(Transactions transactions);

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    Transactions fetchTransaction(int transactionId);

    @Query("SELECT * FROM transactions WHERE isVoid = 0 AND isComplete = 0 AND isBackout = 0 AND isCutoff = 0")
    LiveData<List<Transactions>> fetchResumeTransactions();

    @Query("SELECT * FROM transactions WHERE isVoid = 0 AND isComplete = 0 AND isBackout = 0 AND isCutoff = 0")
    List<Transactions> fetchResumeTransactionsList();

    @Query("SELECT * FROM transactions WHERE isBackout = 0 AND isComplete = 1 AND isCutoff = 0 AND receiptNumber != '' ORDER BY receiptNumber ASC")
    LiveData<List<Transactions>> fetchCompleteTransactions();

    @Query("SELECT * FROM transactions WHERE isBackout = 0 AND isComplete = 1 AND isCutoff = 1 AND DATE(completedAt) = DATE('now') AND receiptNumber != '' ORDER BY receiptNumber ASC")
    LiveData<List<Transactions>> fetchCompleteTransactionsCutOff();

    @Query("SELECT * FROM transactions WHERE isSentToServer = 0")
    LiveData<List<Transactions>> fetchCompleteTransactionToSync();

    @Query("SELECT * FROM transactions WHERE receiptNumber != '' AND isBackout = 0 AND isComplete = 1 AND isCutoff = 0 ORDER BY receiptNumber ASC")
    List<Transactions> fetchTransactionsToCutOff();

    @Query("SELECT * FROM transactions WHERE isBackout = 1")
    List<Transactions> fetchBackoutTransactionsToCutOff();

    @Query("UPDATE transactions SET isSentToServer = 1 WHERE id = :transactionId")
    void updateTransactionSentToServer(int transactionId);

    @Query("SELECT SUM(tenderAmount) AS 'sumTenderAmount' FROM transactions WHERE isBackout = 0 AND isComplete = 1 AND isVoid = 0 AND isCutoff = 0")
    Double sumAllCashTransaction();

    @Query("SELECT SUM(totalCashAmount) AS 'sumTotalCash' FROM transactions WHERE isBackout = 0 AND isComplete = 1 AND isVoid = 0 AND isCutoff = 0")
    Double sumAllTotalCashTransaction();

    @Query("SELECT * FROM transactions WHERE isSentToServer = 0")
    List<Transactions> fetchCompleteTransactionToUpload();

    @Query("UPDATE transactions SET change = :change, grossSales = :grossSales, netSales = :netSales, discountAmount = :discountAmount, vatableSales = :vatableSales, vatExemptSales = :vatExemptSales, vatAmount = :vatAmount, serviceCharge = :serviceCharge, totalUnitCost = :totalUnitCost, totalVoidAmount = :totalVoidAmount, tenderAmount = :tenderAmount, vatExpense = :vatExpense, totalQuantity = :totalQuantity, totalVoidQty = :totalVoidQty, totalReturnAmount = :totalReturnAmount, totalCashAmount = :totalCashAmount, totalZeroRatedAmount = :totalZeroRatedAmount, isSentToServer = 0 WHERE id = :transactionId")
    void updateRecomputeTransaction(int transactionId, double change, double grossSales, double netSales, double discountAmount, double vatableSales, double vatExemptSales, double vatAmount, double serviceCharge, double totalUnitCost, double totalVoidAmount, double tenderAmount, double vatExpense, double totalQuantity, double totalVoidQty, double totalReturnAmount, double totalCashAmount, double totalZeroRatedAmount);

    @Query("UPDATE transactions SET change = :change, tenderAmount = :tenderAmount, totalCashAmount = :totalCashAmount, isSentToServer = 0 WHERE id = :transactionId")
    void updateRecomputeARTransaction(int transactionId, double change, double tenderAmount, double totalCashAmount);

    @Query("UPDATE transactions SET completedAt = :completedAt, cashierId = :cashierId, cashierName = :cashierName, receiptNumber = :receiptNumber, isComplete = 1, isSentToServer = 0, isReturn = :isReturn, totalCashAmount = :totalCashAmount WHERE id = :transactionId")
    void completeTransaction(int transactionId, String completedAt, int cashierId, String cashierName, String receiptNumber, int isReturn, double totalCashAmount);

    @Query("UPDATE transactions SET cashierId = :cashierId, cashierName = :cashierName, receiptNumber = :receiptNumber, isSentToServer = 0, isAccountReceivableRedeem = :isAccountReceivableRedeem , accountReceivableRedeemAt = :accountReceivableRedeemAt WHERE id = :transactionId")
    void completeARTransaction(int transactionId, int cashierId, String cashierName, int isAccountReceivableRedeem, String accountReceivableRedeemAt, String receiptNumber);

    @Query("UPDATE transactions SET isBackoutId = :isBackoutId, backoutBy = :backoutBy, backoutAt = :backoutAt, isBackout = 1, isSentToServer = 0 WHERE id = :transactionId")
    void backoutTransaction(int transactionId, int isBackoutId, String backoutBy, String backoutAt);

    @Query("UPDATE transactions SET isSentToServer = 0, customerName = :customerName WHERE id = :transactionId")
    void resumeTransaction(int transactionId, String customerName);

    @Query("UPDATE transactions SET isResumePrinted = 1, isSentToServer = 0 WHERE id = :transactionId")
    void pauseTransaction(int transactionId);

    @Query("UPDATE transactions SET isSentToServer = 0, customerName = :customerName WHERE id = :transactionId")
    void updateTransactionCustomerName(int transactionId, String customerName);

    @Query("UPDATE transactions SET isSentToServer = 0, remarks = :remarks WHERE id = :transactionId")
    void updateTransactionRemarks(int transactionId, String remarks);

    @Query("UPDATE transactions SET isAccountReceivable = :isAccountReceivable WHERE id = :transactionId")
    void updateTransactionAR(int transactionId, int isAccountReceivable);

    @Query("UPDATE transactions SET chargeAccountId = :chargeAccountId, chargeAccountName = :chargeAccountName WHERE id = :transactionId")
    void updateChargeAccount(int transactionId, int chargeAccountId, String chargeAccountName);

}
