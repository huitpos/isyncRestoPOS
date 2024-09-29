package com.example.isyncpos.objects;

import androidx.annotation.Nullable;

import java.util.List;

public class AccountReceivableTransactionsObject {

    private int id;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private String controlNumber;

    private String receiptNumber;

    private double grossSales;

    private double netSales;

    private double vatableSales;

    private double vatExemptSales;

    private double vatAmount;

    private double discountAmount;

    private double vatExpense;

    private double tenderAmount;

    private double totalCashAmount;

    private double change;

    private double serviceCharge;

    private String type;

    private int cashierId;

    private String cashierName;

    private int takeOrderId;

    private String takeOrderName;

    private double totalQuantity;

    private double totalUnitCost;

    private double totalVoidQty;

    private double totalVoidAmount;

    private String shiftNumber;

    private int isVoid;

    private int voidById;

    private String voidBy;

    private String voidAt;

    private String voidRemarks;

    private int voidCounter;

    private int isBackout;

    private int isBackoutId;

    private String backoutBy;

    private String backoutAt;

    private int chargeAccountId;

    private String chargeAccountName;

    private int isAccountReceivable;

    private int isAccountReceivableRedeem;

    private String accountReceivableRedeemAt;

    private int isSentToServer;

    private int isComplete;

    private String completedAt;

    private int isCutoff;

    private int cutoffId;

    private String cutoffAt;

    private String guestName;

    private int isResumePrinted;

    private int isReturn;

    private double totalReturnAmount;

    private double totalZeroRatedAmount;

    private String customerName;

    private String remarks;

    private String treg;

    private List<AccountReceivableOrdersObject> orders;

    private List<AccountReceivableDiscountsObject> discounts;

    private List<AccountReceivableDiscountOtherInformationsObject> discountOtherInformations;

    private List<AccountReceivableDiscountDetailsObject> discountDetails;

    private List<AccountReceivablePaymentsObject> payments;

    private List<AccountReceivablePaymentOtherInformationsObject> paymentOtherInformations;

    private AccountReceivableOfficialReceiptInformationsObject officialReceiptInformations;

    public AccountReceivableTransactionsObject(int machineNumber, int branchId, int companyId, String controlNumber, String receiptNumber, double grossSales, double netSales, double vatableSales, double vatExemptSales, double vatAmount, double discountAmount, double vatExpense, double tenderAmount, double totalCashAmount, double change, double serviceCharge, String type, int cashierId, String cashierName, int takeOrderId, String takeOrderName, double totalQuantity, double totalUnitCost, double totalVoidQty, double totalVoidAmount, String shiftNumber, int isVoid, int voidById, String voidBy, String voidAt, String voidRemarks, int voidCounter, int isBackout, int isBackoutId, String backoutBy, String backoutAt, int chargeAccountId, String chargeAccountName, int isAccountReceivable, int isAccountReceivableRedeem, String accountReceivableRedeemAt, int isSentToServer, int isComplete, String completedAt, int isCutoff, int cutoffId, String cutoffAt, String guestName, int isResumePrinted, int isReturn, double totalReturnAmount, double totalZeroRatedAmount, String customerName, String remarks, String treg, List<AccountReceivableOrdersObject> orders, List<AccountReceivableDiscountsObject> discounts, List<AccountReceivableDiscountOtherInformationsObject> discountOtherInformations, List<AccountReceivableDiscountDetailsObject> discountDetails, List<AccountReceivablePaymentsObject> payments, List<AccountReceivablePaymentOtherInformationsObject> paymentOtherInformations, @Nullable AccountReceivableOfficialReceiptInformationsObject officialReceiptInformations) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
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
        this.totalCashAmount = totalCashAmount;
        this.change = change;
        this.serviceCharge = serviceCharge;
        this.type = type;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.takeOrderId = takeOrderId;
        this.takeOrderName = takeOrderName;
        this.totalQuantity = totalQuantity;
        this.totalUnitCost = totalUnitCost;
        this.totalVoidQty = totalVoidQty;
        this.totalVoidAmount = totalVoidAmount;
        this.shiftNumber = shiftNumber;
        this.isVoid = isVoid;
        this.voidById = voidById;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.voidRemarks = voidRemarks;
        this.voidCounter = voidCounter;
        this.isBackout = isBackout;
        this.isBackoutId = isBackoutId;
        this.backoutBy = backoutBy;
        this.backoutAt = backoutAt;
        this.chargeAccountId = chargeAccountId;
        this.chargeAccountName = chargeAccountName;
        this.isAccountReceivable = isAccountReceivable;
        this.isAccountReceivableRedeem = isAccountReceivableRedeem;
        this.accountReceivableRedeemAt = accountReceivableRedeemAt;
        this.isSentToServer = isSentToServer;
        this.isComplete = isComplete;
        this.completedAt = completedAt;
        this.isCutoff = isCutoff;
        this.cutoffId = cutoffId;
        this.cutoffAt = cutoffAt;
        this.guestName = guestName;
        this.isResumePrinted = isResumePrinted;
        this.isReturn = isReturn;
        this.totalReturnAmount = totalReturnAmount;
        this.totalZeroRatedAmount = totalZeroRatedAmount;
        this.customerName = customerName;
        this.remarks = remarks;
        this.treg = treg;
        this.orders = orders;
        this.discounts = discounts;
        this.discountOtherInformations = discountOtherInformations;
        this.discountDetails = discountDetails;
        this.payments = payments;
        this.paymentOtherInformations = paymentOtherInformations;
        this.officialReceiptInformations = officialReceiptInformations;
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

    public double getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
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

    public double getTotalVoidQty() {
        return totalVoidQty;
    }

    public void setTotalVoidQty(double totalVoidQty) {
        this.totalVoidQty = totalVoidQty;
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

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
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

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public List<AccountReceivableOrdersObject> getOrders() {
        return orders;
    }

    public void setOrders(List<AccountReceivableOrdersObject> orders) {
        this.orders = orders;
    }

    public List<AccountReceivableDiscountsObject> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<AccountReceivableDiscountsObject> discounts) {
        this.discounts = discounts;
    }

    public List<AccountReceivableDiscountOtherInformationsObject> getDiscountOtherInformations() {
        return discountOtherInformations;
    }

    public void setDiscountOtherInformations(List<AccountReceivableDiscountOtherInformationsObject> discountOtherInformations) {
        this.discountOtherInformations = discountOtherInformations;
    }

    public List<AccountReceivableDiscountDetailsObject> getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(List<AccountReceivableDiscountDetailsObject> discountDetails) {
        this.discountDetails = discountDetails;
    }

    public List<AccountReceivablePaymentsObject> getPayments() {
        return payments;
    }

    public void setPayments(List<AccountReceivablePaymentsObject> payments) {
        this.payments = payments;
    }

    public List<AccountReceivablePaymentOtherInformationsObject> getPaymentOtherInformations() {
        return paymentOtherInformations;
    }

    public void setPaymentOtherInformations(List<AccountReceivablePaymentOtherInformationsObject> paymentOtherInformations) {
        this.paymentOtherInformations = paymentOtherInformations;
    }

    public AccountReceivableOfficialReceiptInformationsObject getOfficialReceiptInformations() {
        return officialReceiptInformations;
    }

    public void setOfficialReceiptInformations(AccountReceivableOfficialReceiptInformationsObject officialReceiptInformations) {
        this.officialReceiptInformations = officialReceiptInformations;
    }

}
