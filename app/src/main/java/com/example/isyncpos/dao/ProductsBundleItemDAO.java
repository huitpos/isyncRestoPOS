package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.ProductsBundleItem;

@Dao
public interface ProductsBundleItemDAO {

    @Insert
    void insert(ProductsBundleItem productsBundleItem);

    @Update
    void update(ProductsBundleItem productsBundleItem);

    @Query("SELECT * FROM productsBundleItem WHERE productCoreId = :productId AND productBundleCoreId = :productBundleId")
    ProductsBundleItem fetchByProductBundle(int productId, int productBundleId);

    @Query("DELETE FROM productsBundleItem WHERE productCoreId = :productId")
    Void removeAllByProductId(int productId);

}
