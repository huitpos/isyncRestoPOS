package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "cutOffPayments",
    indices = {
        @Index(value = {
            "cutOffId",
            "paymentTypeId",
            "endOfDayId",
            "isSentToServer",
            "treg"
        })
    }
)
public class CutOffPayments {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "paymentTypeId")
    private int paymentTypeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "transactionCount")
    private int transactionCount;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "endOfDayId")
    private int endOfDayId;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "treg")
    private String treg;

    public CutOffPayments(int machineNumber, int branchId, int cutOffId, int paymentTypeId, String name, int transactionCount, double amount, int endOfDayId, int isSentToServer, String treg, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.cutOffId = cutOffId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.transactionCount = transactionCount;
        this.amount = amount;
        this.endOfDayId = endOfDayId;
        this.isSentToServer = isSentToServer;
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

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getEndOfDayId() {
        return endOfDayId;
    }

    public void setEndOfDayId(int endOfDayId) {
        this.endOfDayId = endOfDayId;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
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
