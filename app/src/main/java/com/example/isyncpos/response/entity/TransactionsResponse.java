package com.example.isyncpos.response.entity;

import java.util.List;

public class TransactionsResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int transaction_id;

        private int pos_machine_id;

        private String control_number;

        private String receipt_number;

        private double gross_sales;

        private double net_sales;

        private double vatable_sales;

        private double vat_exempt_sales;

        private double vat_amount;

        private double discount_amount;

        private double tender_amount;

        private double change;

        private double service_charge;

        private String type;

        private int cashier_id;

        private String cashier_name;

        private int take_order_id;

        private String take_order_name;

        private double total_unit_cost;

        private double total_void_amount;

        private String shift_number;

        private int is_void;

        private int void_by_id;

        private String void_by;

        private String void_at;

        private int is_back_out;

        private int is_back_out_id;

        private String back_out_by;

        private int charge_account_id;

        private String charge_account_name;

        private int is_account_receivable;

        private int is_sent_to_server;

        private int is_complete;

        private String completed_at;

        private int is_cut_off;

        private int cut_off_id;

        private String cut_off_at;

        private int branch_id;

        private String guest_name;

        private int is_resume_printed;

        private String treg;

        private String backout_at;

        private int total_quantity;

        private int total_void_qty;

        private double vat_expense;

        private int is_return;

        private double total_cash_amount;

        private double total_return_amount;

        private int void_counter;

        private String void_remarks;

        private double total_zero_rated_amount;

        private int is_zero_rated;

        private String customer_name;

        private String remarks;

        private int company_id;

        private int is_account_receivable_redeem;

        private String account_receivable_redeem_at;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCashier_id() {
            return cashier_id;
        }

        public void setCashier_id(int cashier_id) {
            this.cashier_id = cashier_id;
        }

        public String getCashier_name() {
            return cashier_name;
        }

        public void setCashier_name(String cashier_name) {
            this.cashier_name = cashier_name;
        }

        public int getTake_order_id() {
            return take_order_id;
        }

        public void setTake_order_id(int take_order_id) {
            this.take_order_id = take_order_id;
        }

        public String getTake_order_name() {
            return take_order_name;
        }

        public void setTake_order_name(String take_order_name) {
            this.take_order_name = take_order_name;
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

        public String getShift_number() {
            return shift_number;
        }

        public void setShift_number(String shift_number) {
            this.shift_number = shift_number;
        }

        public int getIs_void() {
            return is_void;
        }

        public void setIs_void(int is_void) {
            this.is_void = is_void;
        }

        public int getVoid_by_id() {
            return void_by_id;
        }

        public void setVoid_by_id(int void_by_id) {
            this.void_by_id = void_by_id;
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

        public int getIs_back_out() {
            return is_back_out;
        }

        public void setIs_back_out(int is_back_out) {
            this.is_back_out = is_back_out;
        }

        public int getIs_back_out_id() {
            return is_back_out_id;
        }

        public void setIs_back_out_id(int is_back_out_id) {
            this.is_back_out_id = is_back_out_id;
        }

        public String getBack_out_by() {
            return back_out_by;
        }

        public void setBack_out_by(String back_out_by) {
            this.back_out_by = back_out_by;
        }

        public int getCharge_account_id() {
            return charge_account_id;
        }

        public void setCharge_account_id(int charge_account_id) {
            this.charge_account_id = charge_account_id;
        }

        public String getCharge_account_name() {
            return charge_account_name;
        }

        public void setCharge_account_name(String charge_account_name) {
            this.charge_account_name = charge_account_name;
        }

        public int getIs_account_receivable() {
            return is_account_receivable;
        }

        public void setIs_account_receivable(int is_account_receivable) {
            this.is_account_receivable = is_account_receivable;
        }

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
            this.is_sent_to_server = is_sent_to_server;
        }

        public int getIs_complete() {
            return is_complete;
        }

        public void setIs_complete(int is_complete) {
            this.is_complete = is_complete;
        }

        public String getCompleted_at() {
            return completed_at;
        }

        public void setCompleted_at(String completed_at) {
            this.completed_at = completed_at;
        }

        public int getIs_cut_off() {
            return is_cut_off;
        }

        public void setIs_cut_off(int is_cut_off) {
            this.is_cut_off = is_cut_off;
        }

        public int getCut_off_id() {
            return cut_off_id;
        }

        public void setCut_off_id(int cut_off_id) {
            this.cut_off_id = cut_off_id;
        }

        public String getCut_off_at() {
            return cut_off_at;
        }

        public void setCut_off_at(String cut_off_at) {
            this.cut_off_at = cut_off_at;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public String getGuest_name() {
            return guest_name;
        }

        public void setGuest_name(String guest_name) {
            this.guest_name = guest_name;
        }

        public int getIs_resume_printed() {
            return is_resume_printed;
        }

        public void setIs_resume_printed(int is_resume_printed) {
            this.is_resume_printed = is_resume_printed;
        }

        public String getTreg() {
            return treg;
        }

        public void setTreg(String treg) {
            this.treg = treg;
        }

        public String getBackout_at() {
            return backout_at;
        }

        public void setBackout_at(String backout_at) {
            this.backout_at = backout_at;
        }

        public int getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }

        public int getTotal_void_qty() {
            return total_void_qty;
        }

        public void setTotal_void_qty(int total_void_qty) {
            this.total_void_qty = total_void_qty;
        }

        public double getVat_expense() {
            return vat_expense;
        }

        public void setVat_expense(double vat_expense) {
            this.vat_expense = vat_expense;
        }

        public int getIs_return() {
            return is_return;
        }

        public void setIs_return(int is_return) {
            this.is_return = is_return;
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

        public int getVoid_counter() {
            return void_counter;
        }

        public void setVoid_counter(int void_counter) {
            this.void_counter = void_counter;
        }

        public String getVoid_remarks() {
            return void_remarks;
        }

        public void setVoid_remarks(String void_remarks) {
            this.void_remarks = void_remarks;
        }

        public double getTotal_zero_rated_amount() {
            return total_zero_rated_amount;
        }

        public void setTotal_zero_rated_amount(double total_zero_rated_amount) {
            this.total_zero_rated_amount = total_zero_rated_amount;
        }

        public int getIs_zero_rated() {
            return is_zero_rated;
        }

        public void setIs_zero_rated(int is_zero_rated) {
            this.is_zero_rated = is_zero_rated;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataClass> getData() {
        return data;
    }

    public void setData(List<DataClass> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



}
