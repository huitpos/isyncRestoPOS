package com.example.isyncpos.response.entity;

import java.util.List;

public class DiscountTypesResponse {

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

        private int department_id;

        private String name;

        private String description;

        private String type;

        private int discount;

        private int is_vat_exempt;

        private int is_zero_rated;

        private int is_manual;

        private String status;

        private List<FieldsClass> fields;

        private List<DepartmentClass> departments;

        public class FieldsClass {

            private int id;

            private int payment_type_id;

            private String name;

            private String field_type;

            private String[] options;

            private int is_required;

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

            public int getIs_required() {
                return is_required;
            }

            public void setIs_required(int is_required) {
                this.is_required = is_required;
            }

        }

        public class DepartmentClass {

            private int id;

            private String name;

            private String description;

            private String status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public int getIs_vat_exempt() {
            return is_vat_exempt;
        }

        public void setIs_vat_exempt(int is_vat_exempt) {
            this.is_vat_exempt = is_vat_exempt;
        }

        public List<FieldsClass> getFields() {
            return fields;
        }

        public void setFields(List<FieldsClass> fields) {
            this.fields = fields;
        }

        public List<DepartmentClass> getDepartments() {
            return departments;
        }

        public void setDepartments(List<DepartmentClass> departments) {
            this.departments = departments;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIs_zero_rated() {
            return is_zero_rated;
        }

        public void setIs_zero_rated(int is_zero_rated) {
            this.is_zero_rated = is_zero_rated;
        }

        public int getIs_manual() {
            return is_manual;
        }

        public void setIs_manual(int is_manual) {
            this.is_manual = is_manual;
        }

    }

}
