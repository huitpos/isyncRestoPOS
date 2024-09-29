package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Products;

import java.util.List;

@Dao
public interface ProductsDAO {

    @Insert
    void insert(Products products);

    @Update
    void update(Products products);

    @Query("SELECT * FROM products WHERE status = 1 AND departmentId = :departmentId AND showInCashier = 1 ORDER BY name ASC")
    List<Products> fetchAllByDepartment(int departmentId);

    @Query("SELECT * FROM products WHERE status = 1 AND (name LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%' OR barcode = :search) ORDER BY name ASC")
    List<Products> search(String search);

    @Query("SELECT * FROM products WHERE status = 1 AND barcode = :barcode")
    Products barcode(String barcode);

    @Query("SELECT * FROM products WHERE coreId = :id")
    Products fetchById(int id);

    @Query("SELECT COUNT(*) AS 'productCount' FROM products")
    Long fetchProductCount();

}
