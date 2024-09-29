package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "discountTypeFields",
    indices = {
        @Index(value = {
            "discountTypeId"
        })
    }
)
public class DiscountTypeFields {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "discountTypeId")
    private int discountTypeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "fieldType")
    private String fieldType;

    @ColumnInfo(name = "isRequired")
    private int isRequired;

    public DiscountTypeFields(int coreId, int discountTypeId, String name, String fieldType, int isRequired) {
        this.coreId = coreId;
        this.discountTypeId = discountTypeId;
        this.name = name;
        this.fieldType = fieldType;
        this.isRequired = isRequired;
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

    public int getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(int discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

}
