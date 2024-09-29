package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.repositories.TransactionsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransactionsViewModel extends AndroidViewModel {

    private TransactionsRepository transactionsRepository;

    private MutableLiveData<Transactions> currentTransaction = new MutableLiveData<>();
    private MutableLiveData<Transactions> currentARTransaction = new MutableLiveData<>();
    private LiveData<List<Transactions>> resumeTransactionsLiveData;
    private LiveData<List<Transactions>> completeTransactionsLiveData;
    private LiveData<List<Transactions>> toSyncTransactionsLiveData;
    private LiveData<List<Transactions>> completeTransactionsCutOffLiveData;

    public TransactionsViewModel(Application application){
        super(application);
        transactionsRepository = new TransactionsRepository(application);
        resumeTransactionsLiveData = transactionsRepository.fetchResumeTransactions();
        completeTransactionsLiveData = transactionsRepository.fetchCompleteTransactions();
        toSyncTransactionsLiveData = transactionsRepository.fetchCompleteTransactionToSync();
        completeTransactionsCutOffLiveData = transactionsRepository.fetchCompleteTransactionsCutOff();
    }

    public Long insert(Transactions transactions) throws ExecutionException, InterruptedException {
        return transactionsRepository.insert(transactions);
    }

    public Void update(Transactions transactions) throws ExecutionException, InterruptedException {
        return transactionsRepository.update(transactions);
    }

    public void updateTransactionSentToServer(int transactionId){
        transactionsRepository.updateTransactionSentToServer(transactionId);
    }

    public void updateRecomputeTransaction(int transactionId, double change, double grossSales, double netSales, double discountAmount, double vatableSales, double vatExemptSales, double vatAmount, double serviceCharge, double totalUnitCost, double totalVoidAmount, double tenderAmount, double vatExpense, double totalQuantity, double totalVoidQty, double totalReturnAmount, double totalCashAmount, double totalZeroRatedAmount){
        transactionsRepository.updateRecomputeTransaction(transactionId, change, grossSales, netSales, discountAmount, vatableSales, vatExemptSales, vatAmount, serviceCharge, totalUnitCost, totalVoidAmount, tenderAmount, vatExpense,  totalQuantity, totalVoidQty, totalReturnAmount, totalCashAmount, totalZeroRatedAmount);
    }

    public void updateRecomputeARTransaction(int transactionId, double change, double tenderAmount, double totalCashAmount){
        transactionsRepository.updateRecomputeARTransaction(transactionId, change, tenderAmount, totalCashAmount);
    }

    public void completeTransaction(int transactionId, String completedAt, int cashierId, String cashierName, String receiptNumber, int isReturn, double totalCashAmount){
        transactionsRepository.completeTransaction(transactionId, completedAt, cashierId, cashierName, receiptNumber, isReturn, totalCashAmount);
    }

    public void completeARTransaction(int transactionId, int cashierId, String cashierName, int isAccountReceivableRedeem, String accountReceivableRedeemAt, String receiptNumber){
        transactionsRepository.completeARTransaction(transactionId, cashierId, cashierName, isAccountReceivableRedeem, accountReceivableRedeemAt, receiptNumber);
    }


    public void backoutTransaction(int transactionId, int isBackoutId, String backoutBy, String backoutAt){
        transactionsRepository.backoutTransaction(transactionId, isBackoutId, backoutBy, backoutAt);
    }

    public void resumeTransaction(int transactionId, String customerName){
        transactionsRepository.resumeTransaction(transactionId, customerName);
    }

    public void pauseTransaction(int transactionId){
        transactionsRepository.pauseTransaction(transactionId);
    }

    public void updateTransactionCustomerName(int transactionId, String customerName){
        transactionsRepository.updateTransactionCustomerName(transactionId, customerName);
    }

    public void updateTransactionRemarks(int transactionId, String remarks){
        transactionsRepository.updateTransactionRemarks(transactionId, remarks);
    }

    public Transactions fetchTransaction(int transactionId) throws ExecutionException, InterruptedException {
        return transactionsRepository.fetchTransaction(transactionId);
    }

    public LiveData<List<Transactions>> fetchResumeTransactions() {
        return resumeTransactionsLiveData;
    }

    public LiveData<List<Transactions>> fetchCompleteTransactions(){
        return completeTransactionsLiveData;
    }

    public LiveData<List<Transactions>> fetchCompleteTransactionToSync(){
        return toSyncTransactionsLiveData;
    }

    public List<Transactions> fetchTransactionsToCutOff() throws ExecutionException, InterruptedException {
        return transactionsRepository.fetchTransactionsToCutOff();
    }

    public List<Transactions> fetchBackoutTransactionsToCutOff() throws ExecutionException, InterruptedException {
        return transactionsRepository.fetchBackoutTransactionsToCutOff();
    }

    public List<Transactions> fetchResumeTransactionsList() throws ExecutionException, InterruptedException {
        return transactionsRepository.fetchResumeTransactionsList();
    }

    public List<Transactions> fetchCompleteTransactionToUpload() throws ExecutionException, InterruptedException {
        return transactionsRepository.fetchCompleteTransactionToUpload();
    }

    public LiveData<List<Transactions>> fetchCompleteTransactionsCutOff(){
        return completeTransactionsCutOffLiveData;
    }

    public Double sumAllCashTransaction() throws ExecutionException, InterruptedException {
        return transactionsRepository.sumAllCashTransaction();
    }

    public Double sumAllTotalCashTransaction() throws ExecutionException, InterruptedException {
        return transactionsRepository.sumAllTotalCashTransaction();
    }

    public void setCurrentTransaction(Transactions transactions){
        currentTransaction.setValue(transactions);
    }

    public MutableLiveData<Transactions> getCurrentTransaction(){
        if(currentTransaction == null){
            currentTransaction.setValue(null);
        }
        return currentTransaction;
    }

    public void setARCurrentTransaction(Transactions transactions){
        currentARTransaction.setValue(transactions);
    }

    public MutableLiveData<Transactions> getARCurrentTransaction(){
        if(currentARTransaction == null){
            currentARTransaction.setValue(null);
        }
        return currentARTransaction;
    }

    public void updateTransactionAR(int transactionId, int isAccountReceivable){
        transactionsRepository.updateTransactionAR(transactionId, isAccountReceivable);
    }

}
