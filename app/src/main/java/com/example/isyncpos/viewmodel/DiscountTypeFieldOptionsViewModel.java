package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.repositories.DiscountTypeFieldOptionsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountTypeFieldOptionsViewModel extends AndroidViewModel {

    private DiscountTypeFieldOptionsRepository discountTypeFieldOptionsRepository;

    public DiscountTypeFieldOptionsViewModel(Application application){
        super(application);
        discountTypeFieldOptionsRepository = new DiscountTypeFieldOptionsRepository(application);
    }

    public void insert(DiscountTypeFieldOptions discountTypeFieldOptions){
        discountTypeFieldOptionsRepository.insert(discountTypeFieldOptions);
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeFieldOptionsRepository.delete(discountTypeId);
    }

    public List<DiscountTypeFieldOptions> fetchByDiscountTypeFieldId(int discountTypeFieldId) throws ExecutionException, InterruptedException {
        return discountTypeFieldOptionsRepository.fetchByDiscountTypeFieldId(discountTypeFieldId);
    }

}
