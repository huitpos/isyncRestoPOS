package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.ProductLocations;
import com.example.isyncpos.repositories.ProductLocationsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductLocationsViewModel extends AndroidViewModel {

    private ProductLocationsRepository productLocationsRepository;

    public ProductLocationsViewModel(Application application){
        super(application);
        productLocationsRepository = new ProductLocationsRepository(application);
    }

    public void insert(ProductLocations productLocations){
        productLocationsRepository.insert(productLocations);
    }

    public Void delete(int productId) throws ExecutionException, InterruptedException {
        return productLocationsRepository.delete(productId);
    }

    public List<ProductLocations> fetchProductLocations(int productId) throws ExecutionException, InterruptedException {
        return productLocationsRepository.fetchProductLocations(productId);
    }

}
