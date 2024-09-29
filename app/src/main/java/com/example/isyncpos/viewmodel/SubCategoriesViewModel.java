package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.SubCategories;
import com.example.isyncpos.repositories.SubCategoriesRepository;

import java.util.concurrent.ExecutionException;

public class SubCategoriesViewModel extends AndroidViewModel {

    private SubCategoriesRepository subCategoriesRepository;

    public SubCategoriesViewModel(Application application){
        super(application);
        subCategoriesRepository = new SubCategoriesRepository(application);
    }

    public void insert(SubCategories subCategories){
        subCategoriesRepository.insert(subCategories);
    }

    public void update(SubCategories subCategories){
        subCategoriesRepository.update(subCategories);
    }

    public SubCategories fetchSubCategory(int subCategoryId) throws ExecutionException, InterruptedException {
        return subCategoriesRepository.fetchSubCategory(subCategoryId);
    }


}
