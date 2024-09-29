package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.repositories.DiscountTypesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountTypesViewModel extends AndroidViewModel {

    private DiscountTypesRepository discountTypesRepository;
    private LiveData<List<DiscountTypes>> discountTypesLiveData;

    public DiscountTypesViewModel(Application application){
        super(application);
        discountTypesRepository = new DiscountTypesRepository(application);
        discountTypesLiveData = discountTypesRepository.fetchAll();
    }

    public void insert(DiscountTypes discountTypes){
        discountTypesRepository.insert(discountTypes);
    }

    public void update(DiscountTypes discountTypes){
        discountTypesRepository.update(discountTypes);
    }

    public DiscountTypes fetchById(int coreId) throws ExecutionException, InterruptedException {
        return discountTypesRepository.fetchById(coreId);
    }

    public LiveData<List<DiscountTypes>> fetchAll(){
        return discountTypesLiveData;
    }

    public List<DiscountTypes> fetchAllDiscountTypeInformation() throws ExecutionException, InterruptedException {
        return discountTypesRepository.fetchAllDiscountTypeInformation();
    }

}
