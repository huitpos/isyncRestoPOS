package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.repositories.DiscountTypeFieldsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountTypeFieldsViewModel extends AndroidViewModel {

    private DiscountTypeFieldsRepository discountTypeFieldsRepository;

    public DiscountTypeFieldsViewModel(Application application){
        super(application);
        discountTypeFieldsRepository = new DiscountTypeFieldsRepository(application);
    }

    public void insert(DiscountTypeFields discountTypeFields){
        discountTypeFieldsRepository.insert(discountTypeFields);
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeFieldsRepository.delete(discountTypeId);
    }

    public List<DiscountTypeFields> fetchByDiscountTypeId(int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeFieldsRepository.fetchByDiscountTypeId(discountTypeId);
    }

}
