package com.example.isyncpos.payload;

public class SafekeepingDenominationPayload {

    private int device_id, branch_id, pos_machine_id, safekeeping_denomination_id, safekeeping_id, cash_denomination_id, qty, shift_number, cut_off_id, end_of_day_id, company_id;
    private double amount, total;
    private String name;
    private boolean is_cut_off, is_sent_to_server;

    public SafekeepingDenominationPayload(int device_id, int branch_id, int pos_machine_id, int safekeeping_denomination_id, int safekeeping_id, int cash_denomination_id, int qty, int shift_number, int cut_off_id, int end_of_day_id, double amount, double total, String name, boolean is_cut_off, boolean is_sent_to_server, int company_id) {
        this.device_id = device_id;
        this.branch_id = branch_id;
        this.pos_machine_id = pos_machine_id;
        this.safekeeping_denomination_id = safekeeping_denomination_id;
        this.safekeeping_id = safekeeping_id;
        this.cash_denomination_id = cash_denomination_id;
        this.qty = qty;
        this.shift_number = shift_number;
        this.cut_off_id = cut_off_id;
        this.end_of_day_id = end_of_day_id;
        this.amount = amount;
        this.total = total;
        this.name = name;
        this.is_cut_off = is_cut_off;
        this.is_sent_to_server = is_sent_to_server;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getPos_machine_id() {
        return pos_machine_id;
    }

    public void setPos_machine_id(int pos_machine_id) {
        this.pos_machine_id = pos_machine_id;
    }

    public int getSafekeeping_denomination_id() {
        return safekeeping_denomination_id;
    }

    public void setSafekeeping_denomination_id(int safekeeping_denomination_id) {
        this.safekeeping_denomination_id = safekeeping_denomination_id;
    }

    public int getSafekeeping_id() {
        return safekeeping_id;
    }

    public void setSafekeeping_id(int safekeeping_id) {
        this.safekeeping_id = safekeeping_id;
    }

    public int getCash_denomination_id() {
        return cash_denomination_id;
    }

    public void setCash_denomination_id(int cash_denomination_id) {
        this.cash_denomination_id = cash_denomination_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getShift_number() {
        return shift_number;
    }

    public void setShift_number(int shift_number) {
        this.shift_number = shift_number;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
