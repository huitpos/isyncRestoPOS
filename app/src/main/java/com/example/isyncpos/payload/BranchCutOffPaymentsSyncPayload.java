package com.example.isyncpos.payload;

public class BranchCutOffPaymentsSyncPayload {

    private int device_id;
    private int cut_off_payment_id;
    private int pos_machine_id;
    private int branch_id;
    private int cut_off_id;
    private int payment_type_id;
    private String name;
    private int transaction_count;
    private double amount;
    private int end_of_day_id;
    private boolean is_sent_to_server;
    private String treg;
    private boolean is_cut_off;
    private int company_id;

    public BranchCutOffPaymentsSyncPayload(int device_id, int cut_off_payment_id, int pos_machine_id, int branch_id, int cut_off_id, int payment_type_id, String name, int transaction_count, double amount, int end_of_day_id, boolean is_sent_to_server, String treg, boolean is_cut_off, int company_id) {
        this.device_id = device_id;
        this.cut_off_payment_id = cut_off_payment_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.cut_off_id = cut_off_id;
        this.payment_type_id = payment_type_id;
        this.name = name;
        this.transaction_count = transaction_count;
        this.amount = amount;
        this.end_of_day_id = end_of_day_id;
        this.is_sent_to_server = is_sent_to_server;
        this.treg = treg;
        this.is_cut_off = is_cut_off;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getCut_off_payment_id() {
        return cut_off_payment_id;
    }

    public void setCut_off_payment_id(int cut_off_payment_id) {
        this.cut_off_payment_id = cut_off_payment_id;
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

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransaction_count() {
        return transaction_count;
    }

    public void setTransaction_count(int transaction_count) {
        this.transaction_count = transaction_count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getEnd_of_day_id() {
        return end_of_day_id;
    }

    public void setEnd_of_day_id(int end_of_day_id) {
        this.end_of_day_id = end_of_day_id;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
