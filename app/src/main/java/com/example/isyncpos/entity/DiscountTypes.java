package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "discountTypes",
    indices = {
        @Index(value = {
            "coreId",
            "status"
        })
    }
)
public class DiscountTypes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "departmentId")
    private int departmentId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "discount")
    private int discount;

    @ColumnInfo(name = "isVatExempt")
    private boolean isVatExempt;

    @ColumnInfo(name = "isZeroRated")
    private boolean isZeroRated;

    @ColumnInfo(name = "isManual")
    private boolean isManual;

    @ColumnInfo(name = "status")
    private int status;

    public DiscountTypes(int coreId, int companyId, int departmentId, String name, String description, String type, int discount, boolean isVatExempt, int status, boolean isZeroRated, boolean isManual) {
        this.coreId = coreId;
        this.companyId = companyId;
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.discount = discount;
        this.isVatExempt = isVatExempt;
        this.status = status;
        this.isZeroRated = isZeroRated;
        this.isManual = isManual;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isVatExempt() {
        return isVatExempt;
    }

    public void setVatExempt(boolean vatExempt) {
        isVatExempt = vatExempt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isZeroRated() {
        return isZeroRated;
    }

    public void setZeroRated(boolean zeroRated) {
        isZeroRated = zeroRated;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

}
