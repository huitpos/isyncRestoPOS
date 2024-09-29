package com.example.isyncpos.response.entity;

public class BranchProductSOHResponse {

    private boolean success;
    private DataClass data;
    private String message;
    private String code;

    public class DataClass {

        private int product_id;

        private String name;

        private Double soh;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getSoh() {
            return soh;
        }

        public void setSoh(Double soh) {
            this.soh = soh;
        }

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataClass getData() {
        return data;
    }

    public void setData(DataClass data) {
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
