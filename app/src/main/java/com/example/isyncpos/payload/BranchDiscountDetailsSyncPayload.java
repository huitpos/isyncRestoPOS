package com.example.isyncpos.payload;

public class BranchDiscountDetailsSyncPayload {

    private int device_id, discount_details_id, discount_id, pos_machine_id, branch_id, custom_discount_id, transaction_id, order_id, discount_type_id, void_by_id, cut_off_id, company_id;
    private double value, discount_amount, vat_exempt_amount, vat_expense;
    private String type, void_by, void_at, shift_number, treg;
    private boolean is_void, is_sent_to_server, is_cut_off, is_vat_exempt, is_zero_rated;

    public BranchDiscountDetailsSyncPayload(int device_id, int discount_details_id, int discount_id, int pos_machine_id, int branch_id, int custom_discount_id, int transaction_id, int order_id, int discount_type_id, int void_by_id, int cut_off_id, double value, double discount_amount, double vat_exempt_amount, String type, String void_by, String void_at, String shift_number, String treg, boolean is_void, boolean is_sent_to_server, boolean is_cut_off, boolean is_vat_exempt, double vat_expense, boolean is_zero_rated, int company_id) {
        this.device_id = device_id;
        this.discount_details_id = discount_details_id;
        this.discount_id = discount_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.custom_discount_id = custom_discount_id;
        this.transaction_id = transaction_id;
        this.order_id = order_id;
        this.discount_type_id = discount_type_id;
        this.void_by_id = void_by_id;
        this.cut_off_id = cut_off_id;
        this.value = value;
        this.discount_amount = discount_amount;
        this.vat_exempt_amount = vat_exempt_amount;
        this.type = type;
        this.void_by = void_by;
        this.void_at = void_at;
        this.shift_number = shift_number;
        this.treg = treg;
        this.is_void = is_void;
        this.is_sent_to_server = is_sent_to_server;
        this.is_cut_off = is_cut_off;
        this.is_vat_exempt = is_vat_exempt;
        this.vat_expense = vat_expense;
        this.is_zero_rated = is_zero_rated;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getDiscount_details_id() {
        return discount_details_id;
    }

    public void setDiscount_details_id(int discount_details_id) {
        this.discount_details_id = discount_details_id;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public int getPos_machine_id() {
        return pos_machine_id;
    }

    public void setPos_machine_id(int pos_machine_id) {
        this.pos_machine_id = pos_machine_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getCustom_discount_id() {
        return custom_discount_id;
    }

    public void setCustom_discount_id(int custom_discount_id) {
        this.custom_discount_id = custom_discount_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getDiscount_type_id() {
        return discount_type_id;
    }

    public void setDiscount_type_id(int discount_type_id) {
        this.discount_type_id = discount_type_id;
    }

    public int getVoid_by_id() {
        return void_by_id;
    }

    public void setVoid_by_id(int void_by_id) {
        this.void_by_id = void_by_id;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getVat_exempt_amount() {
        return vat_exempt_amount;
    }

    public void setVat_exempt_amount(double vat_exempt_amount) {
        this.vat_exempt_amount = vat_exempt_amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getShift_number() {
        return shift_number;
    }

    public void setShift_number(String shift_number) {
        this.shift_number = shift_number;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public boolean isIs_void() {
        return is_void;
    }

    public void setIs_void(boolean is_void) {
        this.is_void = is_void;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public boolean isIs_vat_exempt() {
        return is_vat_exempt;
    }

    public void setIs_vat_exempt(boolean is_vat_exempt) {
        this.is_vat_exempt = is_vat_exempt;
    }

    public double getVat_expense() {
        return vat_expense;
    }

    public void setVat_expense(double vat_expense) {
        this.vat_expense = vat_expense;
    }

    public boolean isIs_zero_rated() {
        return is_zero_rated;
    }

    public void setIs_zero_rated(boolean is_zero_rated) {
        this.is_zero_rated = is_zero_rated;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
