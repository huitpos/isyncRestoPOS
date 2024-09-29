package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "products",
    indices = {
        @Index(value = {
            "coreId",
            "barcode",
            "name",
            "departmentId",
            "status",
            "showInCashier"
        })
    }
)
public class Products {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "abbreviation")
    private String abbreviation;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "markupPercentage")
    private double markupPercentage;

    @ColumnInfo(name = "withSerial")
    private int withSerial;

    @ColumnInfo(name = "unitId")
    private int unitId;

    @ColumnInfo(name = "unitName")
    private String unitName;

    @ColumnInfo(name = "unitDescription")
    private String unitDescription;

    @ColumnInfo(name = "departmentId")
    private int departmentId;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    @ColumnInfo(name = "subCategoryId")
    private int subCategoryId;

    @ColumnInfo(name = "isDiscountExempt")
    private int isDiscountExempt;

    @ColumnInfo(name = "isOpenPrice")
    private int isOpenPrice;

    @ColumnInfo(name = "isVatExempt")
    private int isVatExempt;

    @ColumnInfo(name = "minAmountSold")
    private double minAmountSold;

    @ColumnInfo(name = "showInCashier")
    private int showInCashier;

    @ColumnInfo(name = "status")
    private int status;

    public Products(int coreId, String image, String barcode, String code, String name, String description, String abbreviation, double price, double cost, double markupPercentage, int withSerial, int unitId, int departmentId, int categoryId, int subCategoryId, int isDiscountExempt, int isOpenPrice, double minAmountSold, int status, int isVatExempt, int showInCashier, String unitName, String unitDescription) {
        this.coreId = coreId;
        this.image = image;
        this.barcode = barcode;
        this.code = code;
        this.name = name;
        this.description = description;
        this.abbreviation = abbreviation;
        this.price = price;
        this.cost = cost;
        this.markupPercentage = markupPercentage;
        this.withSerial = withSerial;
        this.unitId = unitId;
        this.departmentId = departmentId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.isDiscountExempt = isDiscountExempt;
        this.isOpenPrice = isOpenPrice;
        this.minAmountSold = minAmountSold;
        this.status = status;
        this.isVatExempt = isVatExempt;
        this.showInCashier = showInCashier;
        this.unitName = unitName;
        this.unitDescription = unitDescription;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getMarkupPercentage() {
        return markupPercentage;
    }

    public void setMarkupPercentage(double markupPercentage) {
        this.markupPercentage = markupPercentage;
    }

    public int getWithSerial() {
        return withSerial;
    }

    public void setWithSerial(int withSerial) {
        this.withSerial = withSerial;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getIsDiscountExempt() {
        return isDiscountExempt;
    }

    public void setIsDiscountExempt(int isDiscountExempt) {
        this.isDiscountExempt = isDiscountExempt;
    }

    public int getIsOpenPrice() {
        return isOpenPrice;
    }

    public void setIsOpenPrice(int isOpenPrice) {
        this.isOpenPrice = isOpenPrice;
    }

    public double getMinAmountSold() {
        return minAmountSold;
    }

    public void setMinAmountSold(double minAmountSold) {
        this.minAmountSold = minAmountSold;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsVatExempt() {
        return isVatExempt;
    }

    public void setIsVatExempt(int isVatExempt) {
        this.isVatExempt = isVatExempt;
    }

    public int getShowInCashier() {
        return showInCashier;
    }

    public void setShowInCashier(int showInCashier) {
        this.showInCashier = showInCashier;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

}
