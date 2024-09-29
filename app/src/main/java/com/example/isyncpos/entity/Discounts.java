package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "discounts",
    indices = {
        @Index(value = {
            "isVoid",
            "isSentToServer",
            "isCutOff",
            "transactionId",
            "customDiscountId",
            "discountTypeId"
        })
    }
)
public class Discounts {

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

    @ColumnInfo(name = "customDiscountId")
    private int customDiscountId;

    @ColumnInfo(name = "discountTypeId")
    private int discountTypeId;

    @ColumnInfo(name = "discountName")
    private String discountName;

    @ColumnInfo(name = "value")
    private double value;

    @ColumnInfo(name = "grossAmount")
    private double grossAmount;

    @ColumnInfo(name = "netAmount")
    private double netAmount;

    @ColumnInfo(name = "discountAmount")
    private double discountAmount;

    @ColumnInfo(name = "vatExemptAmount")
    private double vatExemptAmount;

    @ColumnInfo(name = "vatExpense")
    private double vatExpense;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "cashierId")
    private int cashierId;

    @ColumnInfo(name = "cashierName")
    private String cashierName;

    @ColumnInfo(name = "authorizeId")
    private int authorizeId;

    @ColumnInfo(name = "authorizeName")
    private String authorizeName;

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

    @ColumnInfo(name = "isZeroRated")
    private int isZeroRated;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "treg")
    private String treg;

    public Discounts(int machineNumber, int branchId, int transactionId, int customDiscountId, int discountTypeId, String discountName, double value, double discountAmount, double vatExemptAmount, double vatExpense, String type, int cashierId, String cashierName, int authorizeId, String authorizeName, int isVoid, int voidById, String voidBy, String voidAt, int isSentToServer, int isCutOff, int cutOffId, int shiftNumber, String treg, int isZeroRated, double grossAmount, double netAmount, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.transactionId = transactionId;
        this.customDiscountId = customDiscountId;
        this.discountTypeId = discountTypeId;
        this.discountName = discountName;
        this.value = value;
        this.discountAmount = discountAmount;
        this.vatExemptAmount = vatExemptAmount;
        this.vatExpense = vatExpense;
        this.type = type;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.authorizeId = authorizeId;
        this.authorizeName = authorizeName;
        this.isVoid = isVoid;
        this.voidById = voidById;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.isSentToServer = isSentToServer;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
        this.isZeroRated = isZeroRated;
        this.grossAmount = grossAmount;
        this.netAmount = netAmount;
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

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomDiscountId() {
        return customDiscountId;
    }

    public void setCustomDiscountId(int customDiscountId) {
        this.customDiscountId = customDiscountId;
    }

    public int getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(int discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
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

    public int getIsZeroRated() {
        return isZeroRated;
    }

    public void setIsZeroRated(int isZeroRated) {
        this.isZeroRated = isZeroRated;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
