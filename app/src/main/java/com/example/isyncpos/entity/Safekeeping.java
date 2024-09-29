package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "safekeeping",
    indices = {
        @Index(value = {
            "isCutOff",
            "cutOffId",
            "endOfDayId",
            "isSentToServer"
        })
    }
)
public class Safekeeping {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "shortOver")
    private double shortOver;

    @ColumnInfo(name = "cashierId")
    private int cashierId;

    @ColumnInfo(name = "cashierName")
    private String cashierName;

    @ColumnInfo(name = "authorizeId")
    private int authorizeId;

    @ColumnInfo(name = "authorizeName")
    private String authorizeName;

    @ColumnInfo(name = "isAuto")
    private int isAuto;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "endOfDayId")
    private int endOfDayId;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "treg")
    private String treg;

    public Safekeeping(int machineNumber, int branchId, double amount, int cashierId, String cashierName, int authorizeId, String authorizeName, int isCutOff, int cutOffId, int isSentToServer, int shiftNumber, String treg, int endOfDayId, int isAuto, double shortOver, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.amount = amount;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.authorizeId = authorizeId;
        this.authorizeName = authorizeName;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.isSentToServer = isSentToServer;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
        this.endOfDayId = endOfDayId;
        this.isAuto = isAuto;
        this.shortOver = shortOver;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public int getAuthorizeId() {
        return authorizeId;
    }

    public void setAuthorizeId(int authorizeId) {
        this.authorizeId = authorizeId;
    }

    public String getAuthorizeName() {
        return authorizeName;
    }

    public void setAuthorizeName(String authorizeName) {
        this.authorizeName = authorizeName;
    }

    public int getIsCutOff() {
        return isCutOff;
    }

    public void setIsCutOff(int isCutOff) {
        this.isCutOff = isCutOff;
    }

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutoffId) {
        this.cutOffId = cutoffId;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getEndOfDayId() {
        return endOfDayId;
    }

    public void setEndOfDayId(int endOfDayId) {
        this.endOfDayId = endOfDayId;
    }

    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    public double getShortOver() {
        return shortOver;
    }

    public void setShortOver(double shortOver) {
        this.shortOver = shortOver;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
