package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "payments",
    indices = {
        @Index(value = {
            "transactionId",
            "cutOffId",
            "isCutOff",
            "isVoid"
        })
    }
)
public class Payments {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "paymentTypeId")
    private int paymentTypeId;

    @ColumnInfo(name = "paymentTypeName")
    private String paymentTypeName;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "isAdvancePayment")
    private int isAdvancePayment;

    @ColumnInfo(name = "isAccountReceivable")
    private int isAccountReceivable;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "isVoid")
    private int isVoid;

    @ColumnInfo(name = "voidById")
    private int voidById;

    @ColumnInfo(name = "voidBy")
    private String voidBy;

    @ColumnInfo(name = "voidAt")
    private String voidAt;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffAt")
    private String cutOffAt;

    @ColumnInfo(name = "treg")
    private String treg;

    public Payments(int machineNumber, int branchId, int transactionId, int paymentTypeId, String paymentTypeName, double amount, int isAdvancePayment, int shiftNumber, int isSentToServer, int cutOffId, String treg, int isCutOff, String cutOffAt, int isVoid, String voidBy, String voidAt, int voidById, int companyId, int isAccountReceivable) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.transactionId = transactionId;
        this.paymentTypeId = paymentTypeId;
        this.paymentTypeName = paymentTypeName;
        this.amount = amount;
        this.isAdvancePayment = isAdvancePayment;
        this.shiftNumber = shiftNumber;
        this.isSentToServer = isSentToServer;
        this.cutOffId = cutOffId;
        this.treg = treg;
        this.isCutOff = isCutOff;
        this.cutOffAt = cutOffAt;
        this.isVoid = isVoid;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.voidById = voidById;
        this.companyId = companyId;
        this.isAccountReceivable = isAccountReceivable;
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

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIsAdvancePayment() {
        return isAdvancePayment;
    }

    public void setIsAdvancePayment(int isAdvancePayment) {
        this.isAdvancePayment = isAdvancePayment;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getIsCutOff() {
        return isCutOff;
    }

    public void setIsCutOff(int isCutOff) {
        this.isCutOff = isCutOff;
    }

    public String getCutOffAt() {
        return cutOffAt;
    }

    public void setCutOffAt(String cutOffAt) {
        this.cutOffAt = cutOffAt;
    }

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
    }

    public String getVoidBy() {
        return voidBy;
    }

    public void setVoidBy(String voidBy) {
        this.voidBy = voidBy;
    }

    public String getVoidAt() {
        return voidAt;
    }

    public void setVoidAt(String voidAt) {
        this.voidAt = voidAt;
    }

    public int getVoidById() {
        return voidById;
    }

    public void setVoidById(int voidById) {
        this.voidById = voidById;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getIsAccountReceivable() {
        return isAccountReceivable;
    }

    public void setIsAccountReceivable(int isAccountReceivable) {
        this.isAccountReceivable = isAccountReceivable;
    }

}
