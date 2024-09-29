package com.example.isyncpos.response.entity;

import java.util.List;

public class SpotAuditDenominationResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int id;
        private int spot_audit_denomination_id;
        private int pos_machine_id;
        private int branch_id;
        private int company_id;
        private int spot_audit_id;
        private int cash_denomination_id;
        private String name;
        private double amount;
        private int qty;
        private double total;
        private int is_cut_off;
        private int cut_off_id;
        private int is_sent_to_server;
        private int shift_number;
        private String treg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSpot_audit_denomination_id() {
            return spot_audit_denomination_id;
        }

        public void setSpot_audit_denomination_id(int spot_audit_denomination_id) {
            this.spot_audit_denomination_id = spot_audit_denomination_id;
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

        public int getSpot_audit_id() {
            return spot_audit_id;
        }

        public void setSpot_audit_id(int spot_audit_id) {
            this.spot_audit_id = spot_audit_id;
        }

        public int getCash_denomination_id() {
            return cash_denomination_id;
        }

        public void setCash_denomination_id(int cash_denomination_id) {
            this.cash_denomination_id = cash_denomination_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
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

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
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
