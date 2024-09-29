package com.example.isyncpos.objects;

public class AccountReceivableDiscountDetailsObject {

    private int id;

    private int discountId;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private int customDiscountId;

    private int transactionId;

    private int orderId;

    private int discountTypeId;

    private double value;

    private double discountAmount;

    private double vatExemptAmount;

    private double vatExpense;

    private String type;

    private int isVoid;

    private int voidById;

    private String voidBy;

    private String voidAt;

    private int isSentToServer;

    private int isCutOff;

    private int cutOffId;

    private boolean isVatExempt;

    private boolean isZeroRated;

    private int shiftNumber;

    private String treg;

    public AccountReceivableDiscountDetailsObject(int discountId, int machineNumber, int branchId, int companyId, int customDiscountId, int transactionId, int orderId, int discountTypeId, double value, double discountAmount, double vatExemptAmount, double vatExpense, String type, int isVoid, int voidById, String voidBy, String voidAt, int isSentToServer, int isCutOff, int cutOffId, boolean isVatExempt, boolean isZeroRated, int shiftNumber, String treg) {
        this.discountId = discountId;
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
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
        this.isZeroRated = isZeroRated;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public boolean isZeroRated() {
        return isZeroRated;
    }

    public void setZeroRated(boolean zeroRated) {
        isZeroRated = zeroRated;
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

}
