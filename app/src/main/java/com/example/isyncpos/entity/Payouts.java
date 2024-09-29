package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payouts")
public class Payouts {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "controlNumber")
    private String controlNumber;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "cashierId")
    private int cashierId;

    @ColumnInfo(name = "cashierName")
    private String cashierName;

    @ColumnInfo(name = "authorizeId")
    private int authorizeId;

    @ColumnInfo(name = "authorizeName")
    private String authorizeName;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "isCutoff")
    private int isCutoff;

    @ColumnInfo(name = "cutoffId")
    private int cutoffId;

    @ColumnInfo(name = "cutoffAt")
    private String cutoffAt;

    @ColumnInfo(name = "treg")
    private String treg;

    public Payouts(int machineNumber, String controlNumber, double amount, String reason, int cashierId, String cashierName, int authorizeId, String authorizeName, int branchId, int isSentToServer, int isCutoff, int cutoffId, String cutoffAt, String treg, int companyId) {
        this.machineNumber = machineNumber;
        this.controlNumber = controlNumber;
        this.amount = amount;
        this.reason = reason;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.authorizeId = authorizeId;
        this.authorizeName = authorizeName;
        this.branchId = branchId;
        this.isSentToServer = isSentToServer;
        this.isCutoff = isCutoff;
        this.cutoffId = cutoffId;
        this.cutoffAt = cutoffAt;
        this.treg = treg;
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

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getIsCutoff() {
        return isCutoff;
    }

    public void setIsCutoff(int isCutoff) {
        this.isCutoff = isCutoff;
    }

    public int getCutoffId() {
        return cutoffId;
    }

    public void setCutoffId(int cutoffId) {
        this.cutoffId = cutoffId;
    }

    public String getCutoffAt() {
        return cutoffAt;
    }

    public void setCutoffAt(String cutoffAt) {
        this.cutoffAt = cutoffAt;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
