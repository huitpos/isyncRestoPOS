package com.example.isyncpos.response.entity;

import java.util.List;

public class PaymentsResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int payment_id;

        private int pos_machine_id;

        private int branch_id;

        private int transaction_id;

        private int payment_type_id;

        private String payment_type_name;

        private double amount;

        private int is_advance_payment;

        private int shift_number;

        private int is_sent_to_server;

        private int is_cut_off;

        private int cut_off_id;

        private String cut_off_at;

        private String treg;

        private int is_void;

        private String void_at;

        private String void_by;

        private int void_by_id;

        private int company_id;

        private int is_account_receivable;

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

        public String getPayment_type_name() {
            return payment_type_name;
        }

        public void setPayment_type_name(String payment_type_name) {
            this.payment_type_name = payment_type_name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getIs_advance_payment() {
            return is_advance_payment;
        }

        public void setIs_advance_payment(int is_advance_payment) {
            this.is_advance_payment = is_advance_payment;
        }

        public int getShift_number() {
            return shift_number;
        }

        public void setShift_number(int shift_number) {
            this.shift_number = shift_number;
        }

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
            this.is_sent_to_server = is_sent_to_server;
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

        public String getCut_off_at() {
            return cut_off_at;
        }

        public void setCut_off_at(String cut_off_at) {
            this.cut_off_at = cut_off_at;
        }

        public String getTreg() {
            return treg;
        }

        public void setTreg(String treg) {
            this.treg = treg;
        }

        public int getIs_void() {
            return is_void;
        }

        public void setIs_void(int is_void) {
            this.is_void = is_void;
        }

        public String getVoid_at() {
            return void_at;
        }

        public void setVoid_at(String void_at) {
            this.void_at = void_at;
        }

        public String getVoid_by() {
            return void_by;
        }

        public void setVoid_by(String void_by) {
            this.void_by = void_by;
        }

        public int getVoid_by_id() {
            return void_by_id;
        }

        public void setVoid_by_id(int void_by_id) {
            this.void_by_id = void_by_id;
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
