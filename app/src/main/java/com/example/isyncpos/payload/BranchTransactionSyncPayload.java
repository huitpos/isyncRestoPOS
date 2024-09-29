package com.example.isyncpos.payload;

public class BranchTransactionSyncPayload {

    private int transaction_id, pos_machine_id, cashier_id, cut_off_id, branch_id, void_counter, company_id, is_account_receivable_redeem;
    private String control_number, receipt_number, type, cashier_name, take_order_name, shift_number, void_by, void_at, back_out_by, charge_account_name, completed_at, cut_off_at, take_order_id, void_by_id, is_back_out_id, charge_account_id, treg, void_remarks, customer_name, remarks, account_receivable_redeem_at;
    private double gross_sales, net_sales, vatable_sales, vat_exempt_sales, vat_amount, discount_amount, tender_amount, change, service_charge, total_unit_cost, total_void_amount, vat_expense, total_quantity, total_cash_amount, total_return_amount, total_void_qty;
    private boolean is_void, is_back_out, is_account_receivable, is_sent_to_server, is_complete, is_cut_off, is_return;

    public BranchTransactionSyncPayload(int transaction_id, int pos_machine_id, int cashier_id, String take_order_id, String void_by_id, String is_back_out_id, String charge_account_id, int cut_off_id, int branch_id, String control_number, String receipt_number, String type, String cashier_name, String take_order_name, String shift_number, String void_by, String void_at, String back_out_by, String charge_account_name, String completed_at, String cut_off_at, double gross_sales, double net_sales, double vatable_sales, double vat_exempt_sales, double vat_amount, double discount_amount, double tender_amount, double change, double service_charge, double total_unit_cost, double total_void_amount, boolean is_void, boolean is_back_out, boolean is_account_receivable, boolean is_sent_to_server, boolean is_complete, boolean is_cut_off, String treg, double total_quantity, double total_void_qty, double vat_expense, boolean is_return, double total_return_amount, double total_cash_amount, int void_counter, String void_remarks, String customer_name, String remarks, int company_id, int is_account_receivable_redeem, String account_receivable_redeem_at) {
        this.transaction_id = transaction_id;
        this.pos_machine_id = pos_machine_id;
        this.cashier_id = cashier_id;
        this.take_order_id = take_order_id;
        this.void_by_id = void_by_id;
        this.is_back_out_id = is_back_out_id;
        this.charge_account_id = charge_account_id;
        this.cut_off_id = cut_off_id;
        this.branch_id = branch_id;
        this.control_number = control_number;
        this.receipt_number = receipt_number;
        this.type = type;
        this.cashier_name = cashier_name;
        this.take_order_name = take_order_name;
        this.shift_number = shift_number;
        this.void_by = void_by;
        this.void_at = void_at;
        this.back_out_by = back_out_by;
        this.charge_account_name = charge_account_name;
        this.completed_at = completed_at;
        this.cut_off_at = cut_off_at;
        this.gross_sales = gross_sales;
        this.net_sales = net_sales;
        this.vatable_sales = vatable_sales;
        this.vat_exempt_sales = vat_exempt_sales;
        this.vat_amount = vat_amount;
        this.discount_amount = discount_amount;
        this.tender_amount = tender_amount;
        this.change = change;
        this.service_charge = service_charge;
        this.total_unit_cost = total_unit_cost;
        this.total_void_amount = total_void_amount;
        this.is_void = is_void;
        this.is_back_out = is_back_out;
        this.is_account_receivable = is_account_receivable;
        this.is_sent_to_server = is_sent_to_server;
        this.is_complete = is_complete;
        this.is_cut_off = is_cut_off;
        this.treg = treg;
        this.total_quantity = total_quantity;
        this.total_void_qty = total_void_qty;
        this.vat_expense = vat_expense;
        this.is_return = is_return;
        this.total_cash_amount = total_cash_amount;
        this.total_return_amount = total_return_amount;
        this.void_counter = void_counter;
        this.void_remarks = void_remarks;
        this.customer_name = customer_name;
        this.remarks = remarks;
        this.company_id = company_id;
        this.is_account_receivable_redeem = is_account_receivable_redeem;
        this.account_receivable_redeem_at = account_receivable_redeem_at;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getPos_machine_id() {
        return pos_machine_id;
    }

    public void setPos_machine_id(int pos_machine_id) {
        this.pos_machine_id = pos_machine_id;
    }

    public int getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(int cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getTake_order_id() {
        return take_order_id;
    }

    public void setTake_order_id(String take_order_id) {
        this.take_order_id = take_order_id;
    }

    public String getVoid_by_id() {
        return void_by_id;
    }

    public void setVoid_by_id(String void_by_id) {
        this.void_by_id = void_by_id;
    }

    public String getIs_back_out_id() {
        return is_back_out_id;
    }

    public void setIs_back_out_id(String is_back_out_id) {
        this.is_back_out_id = is_back_out_id;
    }

    public String getCharge_account_id() {
        return charge_account_id;
    }

    public void setCharge_account_id(String charge_account_id) {
        this.charge_account_id = charge_account_id;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getControl_number() {
        return control_number;
    }

    public void setControl_number(String control_number) {
        this.control_number = control_number;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCashier_name() {
        return cashier_name;
    }

    public void setCashier_name(String cashier_name) {
        this.cashier_name = cashier_name;
    }

    public String getTake_order_name() {
        return take_order_name;
    }

    public void setTake_order_name(String take_order_name) {
        this.take_order_name = take_order_name;
    }

    public String getShift_number() {
        return shift_number;
    }

    public void setShift_number(String shift_number) {
        this.shift_number = shift_number;
    }

    public String getVoid_by() {
        return void_by;
    }

    public void setVoid_by(String void_by) {
        this.void_by = void_by;
    }

    public String getVoid_at() {
        return void_at;
    }

    public void setVoid_at(String void_at) {
        this.void_at = void_at;
    }

    public String getBack_out_by() {
        return back_out_by;
    }

    public void setBack_out_by(String back_out_by) {
        this.back_out_by = back_out_by;
    }

    public String getCharge_account_name() {
        return charge_account_name;
    }

    public void setCharge_account_name(String charge_account_name) {
        this.charge_account_name = charge_account_name;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public String getCut_off_at() {
        return cut_off_at;
    }

    public void setCut_off_at(String cut_off_at) {
        this.cut_off_at = cut_off_at;
    }

    public double getGross_sales() {
        return gross_sales;
    }

    public void setGross_sales(double gross_sales) {
        this.gross_sales = gross_sales;
    }

    public double getNet_sales() {
        return net_sales;
    }

    public void setNet_sales(double net_sales) {
        this.net_sales = net_sales;
    }

    public double getVatable_sales() {
        return vatable_sales;
    }

    public void setVatable_sales(double vatable_sales) {
        this.vatable_sales = vatable_sales;
    }

    public double getVat_exempt_sales() {
        return vat_exempt_sales;
    }

    public void setVat_exempt_sales(double vat_exempt_sales) {
        this.vat_exempt_sales = vat_exempt_sales;
    }

    public double getVat_amount() {
        return vat_amount;
    }

    public void setVat_amount(double vat_amount) {
        this.vat_amount = vat_amount;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getTender_amount() {
        return tender_amount;
    }

    public void setTender_amount(double tender_amount) {
        this.tender_amount = tender_amount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getService_charge() {
        return service_charge;
    }

    public void setService_charge(double service_charge) {
        this.service_charge = service_charge;
    }

    public double getTotal_unit_cost() {
        return total_unit_cost;
    }

    public void setTotal_unit_cost(double total_unit_cost) {
        this.total_unit_cost = total_unit_cost;
    }

    public double getTotal_void_amount() {
        return total_void_amount;
    }

    public void setTotal_void_amount(double total_void_amount) {
        this.total_void_amount = total_void_amount;
    }

    public boolean isIs_void() {
        return is_void;
    }

    public void setIs_void(boolean is_void) {
        this.is_void = is_void;
    }

    public boolean isIs_back_out() {
        return is_back_out;
    }

    public void setIs_back_out(boolean is_back_out) {
        this.is_back_out = is_back_out;
    }

    public boolean isIs_account_receivable() {
        return is_account_receivable;
    }

    public void setIs_account_receivable(boolean is_account_receivable) {
        this.is_account_receivable = is_account_receivable;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public boolean isIs_complete() {
        return is_complete;
    }

    public void setIs_complete(boolean is_complete) {
        this.is_complete = is_complete;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public double getTotal_void_qty() {
        return total_void_qty;
    }

    public void setTotal_void_qty(double total_void_qty) {
        this.total_void_qty = total_void_qty;
    }

    public double getVat_expense() {
        return vat_expense;
    }

    public void setVat_expense(double vat_expense) {
        this.vat_expense = vat_expense;
    }

    public double getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(double total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getVoid_counter() {
        return void_counter;
    }

    public void setVoid_counter(int void_counter) {
        this.void_counter = void_counter;
    }

    public double getTotal_cash_amount() {
        return total_cash_amount;
    }

    public void setTotal_cash_amount(double total_cash_amount) {
        this.total_cash_amount = total_cash_amount;
    }

    public double getTotal_return_amount() {
        return total_return_amount;
    }

    public void setTotal_return_amount(double total_return_amount) {
        this.total_return_amount = total_return_amount;
    }

    public boolean isIs_return() {
        return is_return;
    }

    public void setIs_return(boolean is_return) {
        this.is_return = is_return;
    }

    public String getVoid_remarks() {
        return void_remarks;
    }

    public void setVoid_remarks(String void_remarks) {
        this.void_remarks = void_remarks;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getIs_account_receivable_redeem() {
        return is_account_receivable_redeem;
    }

    public void setIs_account_receivable_redeem(int is_account_receivable_redeem) {
        this.is_account_receivable_redeem = is_account_receivable_redeem;
    }

    public String getAccount_receivable_redeem_at() {
        return account_receivable_redeem_at;
    }

    public void setAccount_receivable_redeem_at(String account_receivable_redeem_at) {
        this.account_receivable_redeem_at = account_receivable_redeem_at;
    }

}
