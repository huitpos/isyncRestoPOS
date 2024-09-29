package com.example.isyncpos.response.entity;

import java.util.List;

public class SafekeepingResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int device_id;
        private int safekeeping_id;
        private int pos_machine_id;
        private int branch_id;
        private int amount;
        private int cashier_id;
        private String cashier_name;
        private int authorize_id;
        private String authorize_name;
        private int is_cut_off;
        private int cut_off_id;
        private int is_sent_to_server;
        private int shift_number;
        private String treg;
        private int end_of_day_id;
        private int is_auto;
        private double short_over;
        private int company_id;

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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
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

        public int getAuthorize_id() {
            return authorize_id;
        }

        public void setAuthorize_id(int authorize_id) {
            this.authorize_id = authorize_id;
        }

        public String getAuthorize_name() {
            return authorize_name;
        }

        public void setAuthorize_name(String authorize_name) {
            this.authorize_name = authorize_name;
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

        public int getEnd_of_day_id() {
            return end_of_day_id;
        }

        public void setEnd_of_day_id(int end_of_day_id) {
            this.end_of_day_id = end_of_day_id;
        }

        public int getIs_auto() {
            return is_auto;
        }

        public void setIs_auto(int is_auto) {
            this.is_auto = is_auto;
        }

        public double getShort_over() {
            return short_over;
        }

        public void setShort_over(double short_over) {
            this.short_over = short_over;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
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
