package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "discountDetails",
    indices = {
        @Index(value = {
            "isVoid",
            "discountId",
            "isSentToServer",
            "isCutOff",
            "transactionId",
            "orderId",
            "customDiscountId",
            "discountTypeId"
        })
    }
)
public class DiscountDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "discountId")
    private int discountId;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "customDiscountId")
    private int customDiscountId;

    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "orderId")
    private int orderId;

    @ColumnInfo(name = "discountTypeId")
    private int discountTypeId;

    @ColumnInfo(name = "value")
    private double value;

    @ColumnInfo(name = "discountAmount")
    private double discountAmount;

    @ColumnInfo(name = "vatExemptAmount")
    private double vatExemptAmount;

    @ColumnInfo(name = "vatExpense")
    private double vatExpense;

    @ColumnInfo(name = "type")
    private String type;

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

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "isVatExempt")
    private boolean isVatExempt;

    @ColumnInfo(name = "isZeroRated")
    private boolean isZeroRated;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "treg")
    private String treg;

    public DiscountDetails(int discountId, int machineNumber, int branchId, int customDiscountId, int transactionId, int orderId, int discountTypeId, double value, double discountAmount, double vatExemptAmount, double vatExpense, String type, int isVoid, int voidById, String voidBy, String voidAt, int isSentToServer, int isCutOff, int cutOffId, boolean isVatExempt, int shiftNumber, String treg, boolean isZeroRated, int companyId) {
        this.discountId = discountId;
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.customDiscountId = customDiscountId;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.discountTypeId = discountTypeId;
        this.value = value;
        this.discountAmount = discountAmount;
        this.vatExemptAmount = vatExemptAmount;
        this.vatExpense = vatExpense;
        this.type = type;
        this.isVoid = isVoid;
        this.voidById = voidById;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.isSentToServer = isSentToServer;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.isVatExempt = isVatExempt;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
        this.isZeroRated = isZeroRated;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
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

    public int getCustomDiscountId() {
        return customDiscountId;
    }

    public void setCustomDiscountId(int customDiscountId) {
        this.customDiscountId = customDiscountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(int discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getVatExemptAmount() {
        return vatExemptAmount;
    }

    public void setVatExemptAmount(double vatExemptAmount) {
        this.vatExemptAmount = vatExemptAmount;
    }

    public double getVatExpense() {
        return vatExpense;
    }

    public void setVatExpense(double vatExpense) {
        this.vatExpense = vatExpense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
    }

    public int getVoidById() {
        return voidById;
    }

    public void setVoidById(int voidById) {
        this.voidById = voidById;
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

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
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

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public boolean isVatExempt() {
        return isVatExempt;
    }

    public void setVatExempt(boolean vatExempt) {
        isVatExempt = vatExempt;
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

    public boolean isZeroRated() {
        return isZeroRated;
    }

    public void setZeroRated(boolean zeroRated) {
        isZeroRated = zeroRated;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
