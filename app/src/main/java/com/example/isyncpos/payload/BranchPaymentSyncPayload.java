package com.example.isyncpos.payload;

public class BranchPaymentSyncPayload {

    private int payment_id, pos_machine_id, branch_id, transaction_id, payment_type_id, cut_off_id, is_sent_to_server, company_id, is_account_receivable;
    private String payment_type_name, cut_off_at, treg;
    private double amount;
    private boolean is_advance_payment, is_cut_off, is_void;

    public BranchPaymentSyncPayload(int payment_id, int pos_machine_id, int branch_id, int transaction_id, int payment_type_id, int cut_off_id, String payment_type_name, String cut_off_at, double amount, boolean is_advance_payment, boolean is_cut_off, String treg, int is_sent_to_server, boolean is_void, int company_id, int is_account_receivable) {
        this.payment_id = payment_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.transaction_id = transaction_id;
        this.payment_type_id = payment_type_id;
        this.cut_off_id = cut_off_id;
        this.payment_type_name = payment_type_name;
        this.cut_off_at = cut_off_at;
        this.amount = amount;
        this.is_advance_payment = is_advance_payment;
        this.is_cut_off = is_cut_off;
        this.treg = treg;
        this.is_sent_to_server = is_sent_to_server;
        this.is_void = is_void;
        this.company_id = company_id;
        this.is_account_receivable = is_account_receivable;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
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

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public String getPayment_type_name() {
        return payment_type_name;
    }

    public void setPayment_type_name(String payment_type_name) {
        this.payment_type_name = payment_type_name;
    }

    public String getCut_off_at() {
        return cut_off_at;
    }

    public void setCut_off_at(String cut_off_at) {
        this.cut_off_at = cut_off_at;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isIs_advance_payment() {
        return is_advance_payment;
    }

    public void setIs_advance_payment(boolean is_advance_payment) {
        this.is_advance_payment = is_advance_payment;
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

    public int getIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(int is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public boolean isIs_void() {
        return is_void;
    }

    public void setIs_void(boolean is_void) {
        this.is_void = is_void;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getIs_account_receivable() {
        return is_account_receivable;
    }

    public void setIs_account_receivable(int is_account_receivable) {
        this.is_account_receivable = is_account_receivable;
    }

}
