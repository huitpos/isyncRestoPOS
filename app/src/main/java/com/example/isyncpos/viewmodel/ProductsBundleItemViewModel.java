package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.ProductsBundleItem;
import com.example.isyncpos.repositories.ProductsBundleItemRepository;

import java.util.concurrent.ExecutionException;

public class ProductsBundleItemViewModel extends AndroidViewModel {

    private ProductsBundleItemRepository productsBundleItemRepository;

    public ProductsBundleItemViewModel(Application application){
        super(application);
        productsBundleItemRepository = new ProductsBundleItemRepository(application);
    }

    public void insert(ProductsBundleItem productsBundleItem){
        productsBundleItemRepository.insert(productsBundleItem);
    }

    public void update(ProductsBundleItem productsBundleItem){
        productsBundleItemRepository.update(productsBundleItem);
    }

    public ProductsBundleItem fetchByProductBundle(int productId, int productBundleId) throws ExecutionException, InterruptedException {
        return productsBundleItemRepository.fetchByProductBundle(productId, productBundleId);
    }

    public Void removeAllByProductId(int productId) throws ExecutionException, InterruptedException {
        return productsBundleItemRepository.removeAllByProductId(productId);
    }

}
