package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "company")
public class Company {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "clientId")
    private int clientId;

    @ColumnInfo(name = "registeredName")
    private String registeredName;

    @ColumnInfo(name = "companyName")
    private String companyName;

    @ColumnInfo(name = "tradeName")
    private String tradeName;

    @ColumnInfo(name = "logo")
    private String logo;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "posType")
    private String posType;

    @ColumnInfo(name = "barangayId")
    private int barangayId;

    @ColumnInfo(name = "cityId")
    private int cityId;

    @ColumnInfo(name = "provinceId")
    private int provinceId;

    @ColumnInfo(name = "regionId")
    private int regionId;

    @ColumnInfo(name = "slug")
    private String slug;

    @ColumnInfo(name = "unitFloorNumber")
    private String unitFloorNumber;

    @ColumnInfo(name = "regionName")
    private String regionName;

    @ColumnInfo(name = "provinceName")
    private String provinceName;

    @ColumnInfo(name = "cityName")
    private String cityName;

    @ColumnInfo(name = "barangayName")
    private String barangayName;

    public Company(int coreId, int clientId, String registeredName, String companyName, String tradeName, String logo, String country, String phoneNumber, int barangayId, int cityId, int provinceId, int regionId, String slug, String unitFloorNumber, String regionName, String provinceName, String cityName, String barangayName, String posType) {
        this.coreId = coreId;
        this.clientId = clientId;
        this.registeredName = registeredName;
        this.companyName = companyName;
        this.tradeName = tradeName;
        this.logo = logo;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.barangayId = barangayId;
        this.cityId = cityId;
        this.provinceId = provinceId;
        this.regionId = regionId;
        this.slug = slug;
        this.unitFloorNumber = unitFloorNumber;
        this.regionName = regionName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.barangayName = barangayName;
        this.posType = posType;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBarangayId() {
        return barangayId;
    }

    public void setBarangayId(int barangayId) {
        this.barangayId = barangayId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUnitFloorNumber() {
        return unitFloorNumber;
    }

    public void setUnitFloorNumber(String unitFloorNumber) {
        this.unitFloorNumber = unitFloorNumber;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBarangayName() {
        return barangayName;
    }

    public void setBarangayName(String barangayName) {
        this.barangayName = barangayName;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

}
