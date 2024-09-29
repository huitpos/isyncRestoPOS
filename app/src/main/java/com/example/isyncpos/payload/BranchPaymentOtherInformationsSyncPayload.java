package com.example.isyncpos.payload;

public class BranchPaymentOtherInformationsSyncPayload {

    private int device_id;
    private int payment_other_information_id;
    private int pos_machine_id;
    private int branch_id;
    private int transaction_id;
    private int payment_id;
    private String name;
    private String value;
    private boolean is_cut_off;
    private int cut_off_id;
    private boolean is_void;
    private boolean is_sent_to_server;
    private String treg;
    private int company_id;

    public BranchPaymentOtherInformationsSyncPayload(int device_id, int payment_other_information_id, int pos_machine_id, int branch_id, int transaction_id, int payment_id, String name, String value, boolean is_cut_off, int cut_off_id, boolean is_void, boolean is_sent_to_server, String treg, int company_id) {
        this.device_id = device_id;
        this.payment_other_information_id = payment_other_information_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.transaction_id = transaction_id;
        this.payment_id = payment_id;
        this.name = name;
        this.value = value;
        this.is_cut_off = is_cut_off;
        this.cut_off_id = cut_off_id;
        this.is_void = is_void;
        this.is_sent_to_server = is_sent_to_server;
        this.treg = treg;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getPayment_other_information_id() {
        return payment_other_information_id;
    }

    public void setPayment_other_information_id(int payment_other_information_id) {
        this.payment_other_information_id = payment_other_information_id;
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

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
