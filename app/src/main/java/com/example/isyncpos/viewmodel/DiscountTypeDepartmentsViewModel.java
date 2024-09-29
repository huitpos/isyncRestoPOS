package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.repositories.DiscountTypeDepartmentsRepository;

import java.util.concurrent.ExecutionException;

public class DiscountTypeDepartmentsViewModel extends AndroidViewModel {

    private DiscountTypeDepartmentsRepository discountTypeDepartmentsRepository;

    public DiscountTypeDepartmentsViewModel(Application application){
        super(application);
        discountTypeDepartmentsRepository = new DiscountTypeDepartmentsRepository(application);
    }

    public void insert(DiscountTypeDepartments discountTypeDepartments){
        discountTypeDepartmentsRepository.insert(discountTypeDepartments);
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeDepartmentsRepository.delete(discountTypeId);
    }

    public DiscountTypeDepartments fetchByDepartmentDiscountId(int departmentId, int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeDepartmentsRepository.fetchByDepartmentDiscountId(departmentId, discountTypeId);
    }

    public Long fetchDiscountTypeDepartmentCount(int discountTypeId) throws ExecutionException, InterruptedException {
        return discountTypeDepartmentsRepository.fetchDiscountTypeDepartmentCount(discountTypeId);
    }

}
