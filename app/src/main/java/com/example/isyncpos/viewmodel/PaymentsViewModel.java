package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.repositories.PaymentsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PaymentsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Payments>> currentTransactionPayments = new MutableLiveData<>();
    private MutableLiveData<List<Payments>> currentARTransactionPayments = new MutableLiveData<>();
    private PaymentsRepository paymentsRepository;
    private LiveData<List<Payments>> toSyncPaymentLiveData;

    public PaymentsViewModel(Application application){
        super(application);
        paymentsRepository = new PaymentsRepository(application);
        toSyncPaymentLiveData = paymentsRepository.fetchCompletePaymentToSync();
    }

    public Long insert(Payments payments) throws ExecutionException, InterruptedException {
        return paymentsRepository.insert(payments);
    }

    public void update(Payments payments){
        paymentsRepository.update(payments);
    }

    public void remove(Payments payments){
        paymentsRepository.remove(payments);
    }

    public Double fetchTransactionPaymentsSum(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchTransactionPaymentsSum(transactionId);
    }
    public Double fetchARTransactionPaymentsSum(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchARTransactionPaymentsSum(transactionId);
    }

    public List<Payments> fetchTransactionPayments(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchTransactionPayments(transactionId);
    }

    public List<Payments> fetchARTransactionPayments(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchARTransactionPayments(transactionId);
    }

    public List<Payments> fetchCompletePaymentToUpload() throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchCompletePaymentToUpload();
    }

    public Double fetchTransactionPaymentCashSum(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchTransactionPaymentCashSum(transactionId);
    }

    public Double fetchTransactionOthersSum(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchTransactionOthersSum(transactionId);
    }

    public Double fetchARTransactionOthersSum(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchARTransactionOthersSum(transactionId);
    }

    public MutableLiveData<List<Payments>> getCurrentTransactionPayments() {
        if(currentTransactionPayments == null){
            currentTransactionPayments.setValue(null);
        }
        return currentTransactionPayments;
    }

    public void setCurrentTransactionPayments(List<Payments> payments) {
        currentTransactionPayments.setValue(payments);
    }

    public MutableLiveData<List<Payments>> getCurrentARTransactionPayments() {
        if(currentARTransactionPayments == null){
            currentARTransactionPayments.setValue(null);
        }
        return currentARTransactionPayments;
    }

    public void setCurrentARTransactionPayments(List<Payments> payments) {
        currentARTransactionPayments.setValue(payments);
    }

    public LiveData<List<Payments>> fetchCompletePaymentToSync(){
        return toSyncPaymentLiveData;
    }

    public Payments fetchPaymentById(int paymentId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchPaymentById(paymentId);
    }

    public void updatePaymentSentToServer(int paymentId){
        paymentsRepository.updatePaymentSentToServer(paymentId);
    }

    public List<Payments> fetchOtherPaymentsForAR(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchOtherPaymentsForAR(transactionId);
    }

    public List<Payments> fetchOtherPaymentsForNotAR(int transactionId) throws ExecutionException, InterruptedException {
        return paymentsRepository.fetchOtherPaymentsForNotAR(transactionId);
    }

}
