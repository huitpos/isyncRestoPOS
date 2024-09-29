package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.ProductLocations;

import java.util.List;

@Dao
public interface ProductLocationsDAO {

    @Insert
    void insert(ProductLocations productLocations);

    @Query("DELETE FROM productLocations WHERE productId = :productId")
    Void delete(int productId);

    @Query("SELECT * FROM productLocations WHERE productId = :productId AND status = 1")
    List<ProductLocations> fetchProductLocations(int productId);

}
