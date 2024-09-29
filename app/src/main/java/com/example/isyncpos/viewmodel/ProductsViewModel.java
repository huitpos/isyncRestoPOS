package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Products;
import com.example.isyncpos.repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductsViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private MutableLiveData<List<Products>> menuProducts = new MutableLiveData<>();

    public ProductsViewModel(Application application){
        super(application);
        productsRepository = new ProductsRepository(application);
    }

    public void insert(Products products){
        productsRepository.insert(products);
    }

    public void update(Products products){
        productsRepository.update(products);
    }

    public List<Products> fetchAllByDepartment(int departmentId) throws ExecutionException, InterruptedException {
        return productsRepository.fetchAllByDepartment(departmentId);
    }

    public List<Products> search(String search) throws ExecutionException, InterruptedException {
        return productsRepository.search(search);
    }

    public Products barcode(String barcode) throws ExecutionException, InterruptedException {
        return productsRepository.barcode(barcode);
    }

    public Products fetchById(int id) throws ExecutionException, InterruptedException {
        return productsRepository.fetchById(id);
    }

    public Long fetchProductCount() throws ExecutionException, InterruptedException {
        return productsRepository.fetchProductCount();
    }

    public void setMenuProducts(List<Products> products){
        menuProducts.setValue(products);
    }

    public MutableLiveData<List<Products>> fetchMenuProducts(){
        if(menuProducts == null){
            menuProducts.setValue(new ArrayList<>());
        }
        return menuProducts;
    }

}
