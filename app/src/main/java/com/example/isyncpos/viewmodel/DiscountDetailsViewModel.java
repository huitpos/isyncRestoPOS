package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.repositories.DiscountDetailsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountDetailsViewModel extends AndroidViewModel {

    private DiscountDetailsRepository discountDetailsRepository;
    private LiveData<List<DiscountDetails>> toSyncDiscountDetailsLiveData;

    public DiscountDetailsViewModel(Application application){
        super(application);
        discountDetailsRepository = new DiscountDetailsRepository(application);
        toSyncDiscountDetailsLiveData = discountDetailsRepository.fetchCompleteDiscountDetailsToSync();
    }

    public void insert(DiscountDetails discountDetails){
        discountDetailsRepository.insert(discountDetails);
    }

    public Long insertTakeOrderDiscountDetails(DiscountDetails discountDetails) throws ExecutionException, InterruptedException {
        return discountDetailsRepository.insertTakeOrderDiscountDetails(discountDetails);
    }

    public void updateDiscountDetailsSentToServer(int discountDetailsId){
        discountDetailsRepository.updateDiscountDetailsSentToServer(discountDetailsId);
    }

    public void update(DiscountDetails discountDetails){
        discountDetailsRepository.update(discountDetails);
    }

    public List<DiscountDetails> fetchDiscountDetailsByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        return discountDetailsRepository.fetchDiscountDetailsByTransactionId(transactionId);
    }

    public List<DiscountDetails> fetchCompleteDiscountDetailsToUpload() throws ExecutionException, InterruptedException {
        return discountDetailsRepository.fetchCompleteDiscountDetailsToUpload();
    }

    public List<DiscountDetails> fetchDiscountDetailsByTransactionIdWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        return discountDetailsRepository.fetchDiscountDetailsByTransactionIdWithVoid(transactionId);
    }

    public List<DiscountDetails> fetchDiscountDetailsByDiscountIdWithVoid(int discountId) throws ExecutionException, InterruptedException {
        return discountDetailsRepository.fetchDiscountDetailsByDiscountIdWithVoid(discountId);
    }

    public List<DiscountDetails> fetchDiscountDetailsByDiscountId(int discountId) throws ExecutionException, InterruptedException {
        return discountDetailsRepository.fetchDiscountDetailsByDiscountId(discountId);
    }

    public LiveData<List<DiscountDetails>> fetchCompleteDiscountDetailsToSync() {
        return toSyncDiscountDetailsLiveData;
    }

}
