package com.example.isyncpos.response.entity;

import java.util.List;

public class PaymentTypesResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

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

    public class DataClass {

        private int id;

        private int company_id;

        private String name;

        private String description;

        private int is_ar;

        private String logo;

        private String status;

        private List<FieldsClass> fields;

        public class FieldsClass {

            private int id;

            private int payment_type_id;

            private String name;

            private String field_type;

            private String[] options;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPayment_type_id() {
                return payment_type_id;
            }

            public void setPayment_type_id(int payment_type_id) {
                this.payment_type_id = payment_type_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getField_type() {
                return field_type;
            }

            public void setField_type(String field_type) {
                this.field_type = field_type;
            }

            public String[] getOptions() {
                return options;
            }

            public void setOptions(String[] options) {
                this.options = options;
            }

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIs_ar() {
            return is_ar;
        }

        public void setIs_ar(int is_ar) {
            this.is_ar = is_ar;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<FieldsClass> getFields() {
            return fields;
        }

        public void setFields(List<FieldsClass> fields) {
            this.fields = fields;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
