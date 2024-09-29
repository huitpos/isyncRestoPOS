package com.example.isyncpos.response.entity;

import java.util.List;

public class OfficialReceiptInformationResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int id;
        private int official_receipt_information_id;
        private int pos_machine_id;
        private int branch_id;
        private int company_id;
        private int transaction_id;
        private String name;
        private String address;
        private String tin;
        private String business_style;
        private int is_void;
        private int void_by;
        private String void_name;
        private String void_at;
        private int is_sent_to_server;
        private String treg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOfficial_receipt_information_id() {
            return official_receipt_information_id;
        }

        public void setOfficial_receipt_information_id(int official_receipt_information_id) {
            this.official_receipt_information_id = official_receipt_information_id;
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

        public int getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(int transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTin() {
            return tin;
        }

        public void setTin(String tin) {
            this.tin = tin;
        }

        public String getBusiness_style() {
            return business_style;
        }

        public void setBusiness_style(String business_style) {
            this.business_style = business_style;
        }

        public int getIs_void() {
            return is_void;
        }

        public void setIs_void(int is_void) {
            this.is_void = is_void;
        }

        public int getVoid_by() {
            return void_by;
        }

        public void setVoid_by(int void_by) {
            this.void_by = void_by;
        }

        public String getVoid_name() {
            return void_name;
        }

        public void setVoid_name(String void_name) {
            this.void_name = void_name;
        }

        public String getVoid_at() {
            return void_at;
        }

        public void setVoid_at(String void_at) {
            this.void_at = void_at;
        }

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
            this.is_sent_to_server = is_sent_to_server;
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
