package com.example.isyncpos.payload;

public class BranchEndOfDayDiscountsSyncPayload {

    private int device_id;
    private int end_of_day_discount_id;
    private int pos_machine_id;
    private int branch_id;
    private int end_of_day_id;
    private int discount_type_id;
    private String name;
    private int transaction_count;
    private double amount;
    private boolean is_sent_to_server;
    private String treg;
    private int company_id;
    private int is_zero_rated;

    public BranchEndOfDayDiscountsSyncPayload(int device_id, int end_of_day_discount_id, int pos_machine_id, int branch_id, int end_of_day_id, int discount_type_id, String name, int transaction_count, double amount, boolean is_sent_to_server, String treg, int company_id, int is_zero_rated) {
        this.device_id = device_id;
        this.end_of_day_discount_id = end_of_day_discount_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.end_of_day_id = end_of_day_id;
        this.discount_type_id = discount_type_id;
        this.name = name;
        this.transaction_count = transaction_count;
        this.amount = amount;
        this.is_sent_to_server = is_sent_to_server;
        this.treg = treg;
        this.company_id = company_id;
        this.is_zero_rated = is_zero_rated;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getEnd_of_day_discount_id() {
        return end_of_day_discount_id;
    }

    public void setEnd_of_day_discount_id(int end_of_day_discount_id) {
        this.end_of_day_discount_id = end_of_day_discount_id;
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

    public int getEnd_of_day_id() {
        return end_of_day_id;
    }

    public void setEnd_of_day_id(int end_of_day_id) {
        this.end_of_day_id = end_of_day_id;
    }

    public int getDiscount_type_id() {
        return discount_type_id;
    }

    public void setDiscount_type_id(int discount_type_id) {
        this.discount_type_id = discount_type_id;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getIs_zero_rated() {
        return is_zero_rated;
    }

    public void setIs_zero_rated(int is_zero_rated) {
        this.is_zero_rated = is_zero_rated;
    }

}
