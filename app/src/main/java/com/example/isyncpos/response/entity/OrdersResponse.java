package com.example.isyncpos.response.entity;

import java.util.List;

public class OrdersResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int order_id;

        private int pos_machine_id;

        private int transaction_id;

        private int product_id;

        private String code;

        private String name;

        private String description;

        private String abbreviation;

        private double cost;

        private double qty;

        private double amount;

        private double original_amount;

        private double gross;

        private double total;

        private double total_cost;

        private int is_vatable;

        private double vat_amount;

        private double vatable_sales;

        private double vat_exempt_sales;

        private double discount_amount;

        private int department_id;

        private String department_name;

        private int category_id;

        private String category_name;

        private int subcategory_id;

        private String subcategory_name;

        private int unit_id;

        private String unit_name;

        private int is_void;

        private String void_by;

        private String void_at;

        private int is_back_out;

        private int is_back_out_id;

        private String back_out_by;

        private double min_amount_sold;

        private int is_paid;

        private int is_sent_to_server;

        private int is_completed;

        private String completed_at;

        private int branch_id;

        private int shift_number;

        private int is_cut_off;

        private int cut_off_id;

        private String cut_off_at;

        private int discount_details_id;

        private String treg;

        private int is_discount_exempt;

        private int is_open_price;

        private String remarks;

        private double vat_expense;

        private int with_serial;

        private int is_return;

        private String serial_number;

        private int is_zero_rated;

        private double total_zero_rated_amount;

        private int company_id;

        private int price_change_reason_id;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getPos_machine_id() {
            return pos_machine_id;
        }

        public void setPos_machine_id(int pos_machine_id) {
            this.pos_machine_id = pos_machine_id;
        }

        public int getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(int transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getOriginal_amount() {
            return original_amount;
        }

        public void setOriginal_amount(double original_amount) {
            this.original_amount = original_amount;
        }

        public double getGross() {
            return gross;
        }

        public void setGross(double gross) {
            this.gross = gross;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getTotal_cost() {
            return total_cost;
        }

        public void setTotal_cost(double total_cost) {
            this.total_cost = total_cost;
        }

        public int getIs_vatable() {
            return is_vatable;
        }

        public void setIs_vatable(int is_vatable) {
            this.is_vatable = is_vatable;
        }

        public double getVat_amount() {
            return vat_amount;
        }

        public void setVat_amount(double vat_amount) {
            this.vat_amount = vat_amount;
        }

        public double getVatable_sales() {
            return vatable_sales;
        }

        public void setVatable_sales(double vatable_sales) {
            this.vatable_sales = vatable_sales;
        }

        public double getVat_exempt_sales() {
            return vat_exempt_sales;
        }

        public void setVat_exempt_sales(double vat_exempt_sales) {
            this.vat_exempt_sales = vat_exempt_sales;
        }

        public double getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(double discount_amount) {
            this.discount_amount = discount_amount;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(int subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public String getSubcategory_name() {
            return subcategory_name;
        }

        public void setSubcategory_name(String subcategory_name) {
            this.subcategory_name = subcategory_name;
        }

        public int getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(int unit_id) {
            this.unit_id = unit_id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public int getIs_void() {
            return is_void;
        }

        public void setIs_void(int is_void) {
            this.is_void = is_void;
        }

        public String getVoid_by() {
            return void_by;
        }

        public void setVoid_by(String void_by) {
            this.void_by = void_by;
        }

        public String getVoid_at() {
            return void_at;
        }

        public void setVoid_at(String void_at) {
            this.void_at = void_at;
        }

        public int getIs_back_out() {
            return is_back_out;
        }

        public void setIs_back_out(int is_back_out) {
            this.is_back_out = is_back_out;
        }

        public int getIs_back_out_id() {
            return is_back_out_id;
        }

        public void setIs_back_out_id(int is_back_out_id) {
            this.is_back_out_id = is_back_out_id;
        }

        public String getBack_out_by() {
            return back_out_by;
        }

        public void setBack_out_by(String back_out_by) {
            this.back_out_by = back_out_by;
        }

        public double getMin_amount_sold() {
            return min_amount_sold;
        }

        public void setMin_amount_sold(double min_amount_sold) {
            this.min_amount_sold = min_amount_sold;
        }

        public int getIs_paid() {
            return is_paid;
        }

        public void setIs_paid(int is_paid) {
            this.is_paid = is_paid;
        }

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
            this.is_sent_to_server = is_sent_to_server;
        }

        public int getIs_completed() {
            return is_completed;
        }

        public void setIs_completed(int is_completed) {
            this.is_completed = is_completed;
        }

        public String getCompleted_at() {
            return completed_at;
        }

        public void setCompleted_at(String completed_at) {
            this.completed_at = completed_at;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getShift_number() {
            return shift_number;
        }

        public void setShift_number(int shift_number) {
            this.shift_number = shift_number;
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

        public int getDiscount_details_id() {
            return discount_details_id;
        }

        public void setDiscount_details_id(int discount_details_id) {
            this.discount_details_id = discount_details_id;
        }

        public String getTreg() {
            return treg;
        }

        public void setTreg(String treg) {
            this.treg = treg;
        }

        public int getIs_discount_exempt() {
            return is_discount_exempt;
        }

        public void setIs_discount_exempt(int is_discount_exempt) {
            this.is_discount_exempt = is_discount_exempt;
        }

        public int getIs_open_price() {
            return is_open_price;
        }

        public void setIs_open_price(int is_open_price) {
            this.is_open_price = is_open_price;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public double getVat_expense() {
            return vat_expense;
        }

        public void setVat_expense(double vat_expense) {
            this.vat_expense = vat_expense;
        }

        public int getWith_serial() {
            return with_serial;
        }

        public void setWith_serial(int with_serial) {
            this.with_serial = with_serial;
        }

        public int getIs_return() {
            return is_return;
        }

        public void setIs_return(int is_return) {
            this.is_return = is_return;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public int getIs_zero_rated() {
            return is_zero_rated;
        }

        public void setIs_zero_rated(int is_zero_rated) {
            this.is_zero_rated = is_zero_rated;
        }

        public double getTotal_zero_rated_amount() {
            return total_zero_rated_amount;
        }

        public void setTotal_zero_rated_amount(double total_zero_rated_amount) {
            this.total_zero_rated_amount = total_zero_rated_amount;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public int getPrice_change_reason_id() {
            return price_change_reason_id;
        }

        public void setPrice_change_reason_id(int price_change_reason_id) {
            this.price_change_reason_id = price_change_reason_id;
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
