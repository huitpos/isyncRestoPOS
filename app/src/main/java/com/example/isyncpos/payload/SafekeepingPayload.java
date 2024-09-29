package com.example.isyncpos.payload;

public class SafekeepingPayload {

    private int device_id, safekeeping_id, pos_machine_id, branch_id, cashier_id, authorize_id, cut_off_id, shift_number, end_of_day_id, company_id;
    private double amount, short_over;
    private String cashier_name, authorize_name, treg;
    private boolean is_cut_off, is_sent_to_server, is_auto;

    public SafekeepingPayload(int device_id, int safekeeping_id, int pos_machine_id, int branch_id, int cashier_id, int authorize_id, int cut_off_id, int shift_number, int end_of_day_id, double amount, double short_over, String cashier_name, String authorize_name, String treg, boolean is_cut_off, boolean is_sent_to_server, boolean is_auto, int company_id) {
        this.device_id = device_id;
        this.safekeeping_id = safekeeping_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.cashier_id = cashier_id;
        this.authorize_id = authorize_id;
        this.cut_off_id = cut_off_id;
        this.shift_number = shift_number;
        this.end_of_day_id = end_of_day_id;
        this.amount = amount;
        this.short_over = short_over;
        this.cashier_name = cashier_name;
        this.authorize_name = authorize_name;
        this.treg = treg;
        this.is_cut_off = is_cut_off;
        this.is_sent_to_server = is_sent_to_server;
        this.is_auto = is_auto;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getSafekeeping_id() {
        return safekeeping_id;
    }

    public void setSafekeeping_id(int safekeeping_id) {
        this.safekeeping_id = safekeeping_id;
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

    public int getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(int cashier_id) {
        this.cashier_id = cashier_id;
    }

    public int getAuthorize_id() {
        return authorize_id;
    }

    public void setAuthorize_id(int authorize_id) {
        this.authorize_id = authorize_id;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public int getShift_number() {
        return shift_number;
    }

    public void setShift_number(int shift_number) {
        this.shift_number = shift_number;
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

    public double getShort_over() {
        return short_over;
    }

    public void setShort_over(double short_over) {
        this.short_over = short_over;
    }

    public String getCashier_name() {
        return cashier_name;
    }

    public void setCashier_name(String cashier_name) {
        this.cashier_name = cashier_name;
    }

    public String getAuthorize_name() {
        return authorize_name;
    }

    public void setAuthorize_name(String authorize_name) {
        this.authorize_name = authorize_name;
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

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public boolean isIs_auto() {
        return is_auto;
    }

    public void setIs_auto(boolean is_auto) {
        this.is_auto = is_auto;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
