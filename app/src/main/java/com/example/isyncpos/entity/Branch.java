package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "branch")
public class Branch {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "clusterId")
    private int clusterId;

    @ColumnInfo(name = "regionId")
    private int regionId;

    @ColumnInfo(name = "provinceId")
    private int provinceId;

    @ColumnInfo(name = "cityId")
    private int cityId;

    @ColumnInfo(name = "barangayId")
    private int barangayId;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "unitNumber")
    private String unitNumber;

    @ColumnInfo(name = "floorNumber")
    private String floorNumber;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "zip")
    private String zip;

    @ColumnInfo(name = "slug")
    private String slug;

    @ColumnInfo(name = "posType")
    private String posType;

    @ColumnInfo(name = "clusterName")
    private String clusterName;

    @ColumnInfo(name = "regionName")
    private String regionName;

    @ColumnInfo(name = "provinceName")
    private String provinceName;

    @ColumnInfo(name = "cityName")
    private String cityName;

    @ColumnInfo(name = "barangayName")
    private String barangayName;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    public Branch(int coreId, int companyId, int clusterId, int regionId, int provinceId, int cityId, int barangayId, String status, String name, String code, String location, String unitNumber, String floorNumber, String street, String zip, String slug, String posType, String clusterName, String regionName, String provinceName, String cityName, String barangayName, String phoneNumber) {
        this.coreId = coreId;
        this.companyId = companyId;
        this.clusterId = clusterId;
        this.regionId = regionId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.barangayId = barangayId;
        this.status = status;
        this.name = name;
        this.code = code;
        this.location = location;
        this.unitNumber = unitNumber;
        this.floorNumber = floorNumber;
        this.street = street;
        this.zip = zip;
        this.slug = slug;
        this.posType = posType;
        this.clusterName = clusterName;
        this.regionName = regionName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.barangayName = barangayName;
        this.phoneNumber = phoneNumber;
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

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getBarangayId() {
        return barangayId;
    }

    public void setBarangayId(int barangayId) {
        this.barangayId = barangayId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
