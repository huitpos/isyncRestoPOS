package com.example.isyncpos.payload;

public class BranchCashFundSyncPayload {

    private int device_id;
    private int cash_fund_id;
    private int pos_machine_id;
    private int branch_id;
    private double amount;
    private int cashier_id;
    private String cashier_name;
    private boolean is_cut_off;
    private int cut_off_id;
    private int end_of_day_id;
    private boolean is_sent_to_server;
    private int shift_number;
    private String treg;
    private int company_id;

    public BranchCashFundSyncPayload(int device_id, int cash_fund_id, int pos_machine_id, int branch_id, double amount, int cashier_id, String cashier_name, boolean is_cut_off, int cut_off_id, int end_of_day_id, boolean is_sent_to_server, int shift_number, String treg, int company_id) {
        this.device_id = device_id;
        this.cash_fund_id = cash_fund_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.amount = amount;
        this.cashier_id = cashier_id;
        this.cashier_name = cashier_name;
        this.is_cut_off = is_cut_off;
        this.cut_off_id = cut_off_id;
        this.end_of_day_id = end_of_day_id;
        this.is_sent_to_server = is_sent_to_server;
        this.shift_number = shift_number;
        this.treg = treg;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getCash_fund_id() {
        return cash_fund_id;
    }

    public void setCash_fund_id(int cash_fund_id) {
        this.cash_fund_id = cash_fund_id;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
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

    public int getShift_number() {
        return shift_number;
    }

    public void setShift_number(int shift_number) {
        this.shift_number = shift_number;
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

}
