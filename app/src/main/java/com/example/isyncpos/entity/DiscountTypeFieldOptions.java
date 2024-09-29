package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "discountTypeFieldOptions",
    indices = {
        @Index(value = {
            "discountTypeFieldId"
        })
    }
)
public class DiscountTypeFieldOptions {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "discountTypeFieldId")
    private int discountTypeFieldId;

    @ColumnInfo(name = "name")
    private String name;

    public DiscountTypeFieldOptions(int coreId, int discountTypeFieldId, String name) {
        this.coreId = coreId;
        this.discountTypeFieldId = discountTypeFieldId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public int getDiscountTypeFieldId() {
        return discountTypeFieldId;
    }

    public void setDiscountTypeFieldId(int discountTypeFieldId) {
        this.discountTypeFieldId = discountTypeFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
