package com.example.isyncpos.payload;

public class BranchCutOffProductsSyncPayload {

    private int device_id;
    private int cut_off_product_id;
    private int pos_machine_id;
    private int branch_id;
    private int company_id;
    private int cut_off_id;
    private int product_id;
    private double qty;
    private boolean is_cut_off;
    private String cut_off_at;
    private int end_of_day_id;
    private boolean is_sent_to_server;
    private String treg;

    public BranchCutOffProductsSyncPayload(int device_id, int cut_off_product_id, int pos_machine_id, int branch_id, int company_id, int cut_off_id, int product_id, double qty, boolean is_cut_off, String cut_off_at, int end_of_day_id, boolean is_sent_to_server, String treg) {
        this.device_id = device_id;
        this.cut_off_product_id = cut_off_product_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.company_id = company_id;
        this.cut_off_id = cut_off_id;
        this.product_id = product_id;
        this.qty = qty;
        this.is_cut_off = is_cut_off;
        this.cut_off_at = cut_off_at;
        this.end_of_day_id = end_of_day_id;
        this.is_sent_to_server = is_sent_to_server;
        this.treg = treg;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getCut_off_product_id() {
        return cut_off_product_id;
    }

    public void setCut_off_product_id(int cut_off_product_id) {
        this.cut_off_product_id = cut_off_product_id;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public String getCut_off_at() {
        return cut_off_at;
    }

    public void setCut_off_at(String cut_off_at) {
        this.cut_off_at = cut_off_at;
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

}
