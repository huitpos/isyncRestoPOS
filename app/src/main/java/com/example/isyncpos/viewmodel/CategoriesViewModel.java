package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Categories;
import com.example.isyncpos.repositories.CategoriesRepository;

import java.util.concurrent.ExecutionException;

public class CategoriesViewModel extends AndroidViewModel {

    private CategoriesRepository categoriesRepository;

    public CategoriesViewModel(Application application){
        super(application);
        categoriesRepository = new CategoriesRepository(application);
    }

    public void insert(Categories categories){
        categoriesRepository.insert(categories);
    }

    public void update(Categories categories){
        categoriesRepository.update(categories);
    }

    public Categories fetchCategory(int categoryId) throws ExecutionException, InterruptedException {
        return categoriesRepository.fetchCategory(categoryId);
    }

}
