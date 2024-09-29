package com.example.isyncpos.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "transactions",
    indices = {
        @Index(value = {
            "isVoid",
            "isAccountReceivable",
            "isSentToServer",
            "isComplete",
            "isCutoff",
            "isBackout",
            "guestName",
            "treg",
            "isReturn",
            "customerName",
            "isAccountReceivableRedeem",
            "accountReceivableRedeemAt"
        })
    }
)
public class Transactions {

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

    @ColumnInfo(name = "receiptNumber")
    private String receiptNumber;

    @ColumnInfo(name = "grossSales")
    private double grossSales;

    @ColumnInfo(name = "netSales")
    private double netSales;

    @ColumnInfo(name = "vatableSales")
    private double vatableSales;

    @ColumnInfo(name = "vatExemptSales")
    private double vatExemptSales;

    @ColumnInfo(name = "vatAmount")
    private double vatAmount;

    @ColumnInfo(name = "discountAmount")
    private double discountAmount;

    @ColumnInfo(name = "vatExpense")
    private double vatExpense;

    @ColumnInfo(name = "tenderAmount")
    private double tenderAmount;

    @ColumnInfo(name = "totalCashAmount")
    private double totalCashAmount;

    @ColumnInfo(name = "change")
    private double change;

    @ColumnInfo(name = "serviceCharge")
    private double serviceCharge;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "cashierId")
    private int cashierId;

    @ColumnInfo(name = "cashierName")
    private String cashierName;

    @ColumnInfo(name = "takeOrderId")
    private int takeOrderId;

    @ColumnInfo(name = "takeOrderName")
    private String takeOrderName;

    @ColumnInfo(name = "totalQuantity")
    private double totalQuantity;

    @ColumnInfo(name = "totalUnitCost")
    private double totalUnitCost;

    @ColumnInfo(name = "totalVoidQty")
    private double totalVoidQty;

    @ColumnInfo(name = "totalVoidAmount")
    private double totalVoidAmount;

    @ColumnInfo(name = "shiftNumber")
    private String shiftNumber;

    @ColumnInfo(name = "isVoid")
    private int isVoid;

    @ColumnInfo(name = "voidById")
    private int voidById;

    @ColumnInfo(name = "voidBy")
    private String voidBy;

    @ColumnInfo(name = "voidAt")
    private String voidAt;

    @ColumnInfo(name = "voidRemarks")
    private String voidRemarks;

    @ColumnInfo(name = "voidCounter")
    private int voidCounter;

    @ColumnInfo(name = "isBackout")
    private int isBackout;

    @ColumnInfo(name = "isBackoutId")
    private int isBackoutId;

    @ColumnInfo(name = "backoutBy")
    private String backoutBy;

    @ColumnInfo(name = "backoutAt")
    private String backoutAt;

    @ColumnInfo(name = "chargeAccountId")
    private int chargeAccountId;

    @ColumnInfo(name = "chargeAccountName")
    private String chargeAccountName;

    @ColumnInfo(name = "isAccountReceivable")
    private int isAccountReceivable;

    @ColumnInfo(name = "isAccountReceivableRedeem")
    private int isAccountReceivableRedeem;

    @ColumnInfo(name = "accountReceivableRedeemAt")
    private String accountReceivableRedeemAt;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "isComplete")
    private int isComplete;

    @ColumnInfo(name = "completedAt")
    private String completedAt;

    @ColumnInfo(name = "isCutoff")
    private int isCutoff;

    @ColumnInfo(name = "cutoffId")
    private int cutoffId;

    @ColumnInfo(name = "cutoffAt")
    private String cutoffAt;

    @ColumnInfo(name = "guestName")
    private String guestName;

    @ColumnInfo(name = "isResumePrinted")
    private int isResumePrinted;

    @ColumnInfo(name = "isReturn")
    private int isReturn;

    @ColumnInfo(name = "totalReturnAmount")
    private double totalReturnAmount;

    @ColumnInfo(name = "totalZeroRatedAmount")
    private double totalZeroRatedAmount;

    @Nullable
    @ColumnInfo(name = "customerName")
    private String customerName;

    @ColumnInfo(name = "remarks")
    private String remarks;

    @ColumnInfo(name = "treg")
    private String treg;

    public Transactions(int machineNumber, String controlNumber, String receiptNumber, double grossSales, double netSales, double vatableSales, double vatExemptSales, double vatAmount, double discountAmount, double vatExpense, double tenderAmount, double change, double serviceCharge, String type, int cashierId, String cashierName, int takeOrderId, String takeOrderName, double totalQuantity, double totalUnitCost, double totalVoidAmount, String shiftNumber, int isVoid, int voidById, String voidBy, String voidAt, int isBackout, int isBackoutId, String backoutBy, String backoutAt, int chargeAccountId, String chargeAccountName, int isAccountReceivable, int isSentToServer, int isComplete, String completedAt, int isCutoff, int cutoffId, String cutoffAt, int branchId, String guestName, int isResumePrinted, String treg, double totalVoidQty, int isReturn, double totalReturnAmount, double totalCashAmount, String voidRemarks, int voidCounter, double totalZeroRatedAmount, String customerName, String remarks, int companyId, int isAccountReceivableRedeem, String accountReceivableRedeemAt) {
        this.machineNumber = machineNumber;
        this.controlNumber = controlNumber;
        this.receiptNumber = receiptNumber;
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.vatableSales = vatableSales;
        this.vatExemptSales = vatExemptSales;
        this.vatAmount = vatAmount;
        this.discountAmount = discountAmount;
        this.vatExpense = vatExpense;
        this.tenderAmount = tenderAmount;
        this.change = change;
        this.serviceCharge = serviceCharge;
        this.type = type;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.takeOrderId = takeOrderId;
        this.takeOrderName = takeOrderName;
        this.totalQuantity = totalQuantity;
        this.totalUnitCost = totalUnitCost;
        this.totalVoidAmount = totalVoidAmount;
        this.shiftNumber = shiftNumber;
        this.isVoid = isVoid;
        this.voidById = voidById;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.isBackout = isBackout;
        this.isBackoutId = isBackoutId;
        this.backoutBy = backoutBy;
        this.backoutAt = backoutAt;
        this.chargeAccountId = chargeAccountId;
        this.chargeAccountName = chargeAccountName;
        this.isAccountReceivable = isAccountReceivable;
        this.isSentToServer = isSentToServer;
        this.isComplete = isComplete;
        this.completedAt = completedAt;
        this.isCutoff = isCutoff;
        this.cutoffId = cutoffId;
        this.cutoffAt = cutoffAt;
        this.branchId = branchId;
        this.guestName = guestName;
        this.isResumePrinted = isResumePrinted;
        this.treg = treg;
        this.totalVoidQty = totalVoidQty;
        this.isReturn = isReturn;
        this.totalReturnAmount = totalReturnAmount;
        this.totalCashAmount = totalCashAmount;
        this.voidRemarks = voidRemarks;
        this.voidCounter = voidCounter;
        this.totalZeroRatedAmount = totalZeroRatedAmount;
        this.customerName = customerName;
        this.remarks = remarks;
        this.companyId = companyId;
        this.isAccountReceivableRedeem = isAccountReceivableRedeem;
        this.accountReceivableRedeemAt = accountReceivableRedeemAt;
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

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(double grossSales) {
        this.grossSales = grossSales;
    }

    public double getNetSales() {
        return netSales;
    }

    public void setNetSales(double netSales) {
        this.netSales = netSales;
    }

    public double getVatableSales() {
        return vatableSales;
    }

    public void setVatableSales(double vatableSales) {
        this.vatableSales = vatableSales;
    }

    public double getVatExemptSales() {
        return vatExemptSales;
    }

    public void setVatExemptSales(double vatExemptSales) {
        this.vatExemptSales = vatExemptSales;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getVatExpense() {
        return vatExpense;
    }

    public void setVatExpense(double vatExpense) {
        this.vatExpense = vatExpense;
    }

    public double getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(double tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
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

    public int getTakeOrderId() {
        return takeOrderId;
    }

    public void setTakeOrderId(int takeOrderId) {
        this.takeOrderId = takeOrderId;
    }

    public String getTakeOrderName() {
        return takeOrderName;
    }

    public void setTakeOrderName(String takeOrderName) {
        this.takeOrderName = takeOrderName;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalUnitCost() {
        return totalUnitCost;
    }

    public void setTotalUnitCost(double totalUnitCost) {
        this.totalUnitCost = totalUnitCost;
    }

    public double getTotalVoidAmount() {
        return totalVoidAmount;
    }

    public void setTotalVoidAmount(double totalVoidAmount) {
        this.totalVoidAmount = totalVoidAmount;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(String shiftNumber) {
        this.shiftNumber = shiftNumber;
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

    public int getIsBackout() {
        return isBackout;
    }

    public void setIsBackout(int isBackout) {
        this.isBackout = isBackout;
    }

    public int getIsBackoutId() {
        return isBackoutId;
    }

    public void setIsBackoutId(int isBackoutId) {
        this.isBackoutId = isBackoutId;
    }

    public String getBackoutBy() {
        return backoutBy;
    }

    public void setBackoutBy(String backoutBy) {
        this.backoutBy = backoutBy;
    }

    public String getBackoutAt() {
        return backoutAt;
    }

    public void setBackoutAt(String backoutAt) {
        this.backoutAt = backoutAt;
    }

    public int getChargeAccountId() {
        return chargeAccountId;
    }

    public void setChargeAccountId(int chargeAccountId) {
        this.chargeAccountId = chargeAccountId;
    }

    public String getChargeAccountName() {
        return chargeAccountName;
    }

    public void setChargeAccountName(String chargeAccountName) {
        this.chargeAccountName = chargeAccountName;
    }

    public int getIsAccountReceivable() {
        return isAccountReceivable;
    }

    public void setIsAccountReceivable(int isAccountReceivable) {
        this.isAccountReceivable = isAccountReceivable;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public String getCompletedAt() {
        return completedAt;
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

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getIsResumePrinted() {
        return isResumePrinted;
    }

    public void setIsResumePrinted(int isResumePrinted) {
        this.isResumePrinted = isResumePrinted;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public double getTotalVoidQty() {
        return totalVoidQty;
    }

    public void setTotalVoidQty(double totalVoidQty) {
        this.totalVoidQty = totalVoidQty;
    }

    public int getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public double getTotalReturnAmount() {
        return totalReturnAmount;
    }

    public void setTotalReturnAmount(double totalReturnAmount) {
        this.totalReturnAmount = totalReturnAmount;
    }

    public double getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public String getVoidRemarks() {
        return voidRemarks;
    }

    public void setVoidRemarks(String voidRemarks) {
        this.voidRemarks = voidRemarks;
    }

    public int getVoidCounter() {
        return voidCounter;
    }

    public void setVoidCounter(int voidCounter) {
        this.voidCounter = voidCounter;
    }

    public double getTotalZeroRatedAmount() {
        return totalZeroRatedAmount;
    }

    public void setTotalZeroRatedAmount(double totalZeroRatedAmount) {
        this.totalZeroRatedAmount = totalZeroRatedAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getIsAccountReceivableRedeem() {
        return isAccountReceivableRedeem;
    }

    public void setIsAccountReceivableRedeem(int isAccountReceivableRedeem) {
        this.isAccountReceivableRedeem = isAccountReceivableRedeem;
    }

    public String getAccountReceivableRedeemAt() {
        return accountReceivableRedeemAt;
    }

    public void setAccountReceivableRedeemAt(String accountReceivableRedeemAt) {
        this.accountReceivableRedeemAt = accountReceivableRedeemAt;
    }

}
