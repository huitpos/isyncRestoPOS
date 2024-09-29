package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.repositories.DiscountOtherInformationsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountOtherInformationsViewModel extends AndroidViewModel {

    private DiscountOtherInformationsRepository discountOtherInformationsRepository;
    private LiveData<List<DiscountOtherInformations>> discountOtherInformationsLiveData;

    public DiscountOtherInformationsViewModel(Application application){
        super(application);
        discountOtherInformationsRepository = new DiscountOtherInformationsRepository(application);
        discountOtherInformationsLiveData = discountOtherInformationsRepository.fetchCompleteDiscountOtherInformationsToSync();
    }

    public void insert(DiscountOtherInformations discountOtherInformations){
        discountOtherInformationsRepository.insert(discountOtherInformations);
    }

    public void update(DiscountOtherInformations discountOtherInformations){
        discountOtherInformationsRepository.update(discountOtherInformations);
    }

    public void updateDiscountOtherInformationSentToServer(int discountOtherInformationId){
        discountOtherInformationsRepository.updateDiscountOtherInformationSentToServer(discountOtherInformationId);
    }

    public List<DiscountOtherInformations> fetchByTransactionDiscountId(int discountId) throws ExecutionException, InterruptedException {
        return discountOtherInformationsRepository.fetchByTransactionDiscountId(discountId);
    }

    public LiveData<List<DiscountOtherInformations>> fetchCompleteDiscountOtherInformationsToSync(){
        return discountOtherInformationsLiveData;
    }

    public List<DiscountOtherInformations> fetchDiscountOtherInformationToCutOff() throws ExecutionException, InterruptedException {
        return discountOtherInformationsRepository.fetchDiscountOtherInformationToCutOff();
    }

    public List<DiscountOtherInformations> fetchDiscountOtherInformationByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        return discountOtherInformationsRepository.fetchDiscountOtherInformationByTransactionId(transactionId);
    }

}
