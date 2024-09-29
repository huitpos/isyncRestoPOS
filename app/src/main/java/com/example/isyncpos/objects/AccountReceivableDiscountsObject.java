package com.example.isyncpos.objects;

public class AccountReceivableDiscountsObject {

    private int id;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private int transactionId;

    private int customDiscountId;

    private int discountTypeId;

    private String discountName;

    private double value;

    private double grossAmount;

    private double netAmount;

    private double discountAmount;

    private double vatExemptAmount;

    private double vatExpense;

    private String type;

    private int cashierId;

    private String cashierName;

    private int authorizeId;

    private String authorizeName;

    private int isVoid;

    private int voidById;

    private String voidBy;

    private String voidAt;

    private int isSentToServer;

    private int isZeroRated;

    private int isCutOff;

    private int cutOffId;

    private int shiftNumber;

    private String treg;

    public AccountReceivableDiscountsObject(int machineNumber, int branchId, int companyId, int transactionId, int customDiscountId, int discountTypeId, String discountName, double value, double grossAmount, double netAmount, double discountAmount, double vatExemptAmount, double vatExpense, String type, int cashierId, String cashierName, int authorizeId, String authorizeName, int isVoid, int voidById, String voidBy, String voidAt, int isSentToServer, int isZeroRated, int isCutOff, int cutOffId, int shiftNumber, String treg) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
        this.transactionId = transactionId;
        this.customDiscountId = customDiscountId;
        this.discountTypeId = discountTypeId;
        this.discountName = discountName;
        this.value = value;
        this.grossAmount = grossAmount;
        this.netAmount = netAmount;
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
        this.isZeroRated = isZeroRated;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public int getIsZeroRated() {
        return isZeroRated;
    }

    public void setIsZeroRated(int isZeroRated) {
        this.isZeroRated = isZeroRated;
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

}
