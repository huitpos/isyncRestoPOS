package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productsBundleItem")
public class ProductsBundleItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "productCoreId")
    private int productCoreId;

    @ColumnInfo(name = "productBundleCoreId")
    private int productBundleCoreId;

    public ProductsBundleItem(int productCoreId, int productBundleCoreId) {
        this.productCoreId = productCoreId;
        this.productBundleCoreId = productBundleCoreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductCoreId() {
        return productCoreId;
    }

    public void setProductCoreId(int productCoreId) {
        this.productCoreId = productCoreId;
    }

    public int getProductBundleCoreId() {
        return productBundleCoreId;
    }

    public void setProductBundleCoreId(int productBundleCoreId) {
        this.productBundleCoreId = productBundleCoreId;
    }

}
