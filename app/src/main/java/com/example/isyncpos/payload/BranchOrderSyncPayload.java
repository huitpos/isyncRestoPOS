package com.example.isyncpos.payload;

public class BranchOrderSyncPayload {

    private int order_id, pos_machine_id, transaction_id, product_id, department_id, category_id, subcategory_id, unit_id, branch_id, cut_off_id, company_id, price_change_reason_id;
    private String code, name, description, abbreviation, department_name, category_name, subcategory_name, unit_name, void_by, void_at, back_out_by, completed_at, cut_off_at, is_back_out_id, treg;
    private double cost, qty, amount, original_amount, gross, total, total_cost, vat_amount, vatable_sales, vat_exempt_sales, discount_amount, min_amount_sold, vat_expense;
    private boolean is_vatable, is_void, is_back_out, is_paid, is_sent_to_server, is_completed, is_cut_off, is_discount_exempt, is_open_price, with_serial, is_return, is_zero_rated;

    public BranchOrderSyncPayload(int order_id, int pos_machine_id, int transaction_id, int product_id, int department_id, int category_id, int subcategory_id, int unit_id, String is_back_out_id, int branch_id, String code, String name, String description, String abbreviation, String department_name, String category_name, String subcategory_name, String unit_name, String void_by, String void_at, String back_out_by, String completed_at, double cost, double qty, double amount, double original_amount, double gross, double total, double total_cost, double vat_amount, double vatable_sales, double vat_exempt_sales, double discount_amount, double min_amount_sold, boolean is_vatable, boolean is_void, boolean is_back_out, boolean is_paid, boolean is_sent_to_server, boolean is_completed, boolean is_cut_off, int cut_off_id, String cut_off_at, String treg, boolean is_discount_exempt, boolean is_open_price, boolean with_serial, double vat_expense, boolean is_return, boolean is_zero_rated, int company_id, int price_change_reason_id) {
        this.order_id = order_id;
        this.pos_machine_id = pos_machine_id;
        this.transaction_id = transaction_id;
        this.product_id = product_id;
        this.department_id = department_id;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.unit_id = unit_id;
        this.is_back_out_id = is_back_out_id;
        this.branch_id = branch_id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.abbreviation = abbreviation;
        this.department_name = department_name;
        this.category_name = category_name;
        this.subcategory_name = subcategory_name;
        this.unit_name = unit_name;
        this.void_by = void_by;
        this.void_at = void_at;
        this.back_out_by = back_out_by;
        this.completed_at = completed_at;
        this.cost = cost;
        this.qty = qty;
        this.amount = amount;
        this.original_amount = original_amount;
        this.gross = gross;
        this.total = total;
        this.total_cost = total_cost;
        this.vat_amount = vat_amount;
        this.vatable_sales = vatable_sales;
        this.vat_exempt_sales = vat_exempt_sales;
        this.discount_amount = discount_amount;
        this.min_amount_sold = min_amount_sold;
        this.is_vatable = is_vatable;
        this.is_void = is_void;
        this.is_back_out = is_back_out;
        this.is_paid = is_paid;
        this.is_sent_to_server = is_sent_to_server;
        this.is_completed = is_completed;
        this.is_cut_off = is_cut_off;
        this.cut_off_id = cut_off_id;
        this.cut_off_at = cut_off_at;
        this.treg = treg;
        this.is_discount_exempt = is_discount_exempt;
        this.is_open_price = is_open_price;
        this.with_serial = with_serial;
        this.vat_expense = vat_expense;
        this.is_return = is_return;
        this.is_zero_rated = is_zero_rated;
        this.company_id = company_id;
        this.price_change_reason_id = price_change_reason_id;
    }

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

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getIs_back_out_id() {
        return is_back_out_id;
    }

    public void setIs_back_out_id(String is_back_out_id) {
        this.is_back_out_id = is_back_out_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
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

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
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

    public String getBack_out_by() {
        return back_out_by;
    }

    public void setBack_out_by(String back_out_by) {
        this.back_out_by = back_out_by;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
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

    public double getMin_amount_sold() {
        return min_amount_sold;
    }

    public void setMin_amount_sold(double min_amount_sold) {
        this.min_amount_sold = min_amount_sold;
    }

    public boolean isIs_vatable() {
        return is_vatable;
    }

    public void setIs_vatable(boolean is_vatable) {
        this.is_vatable = is_vatable;
    }

    public boolean isIs_void() {
        return is_void;
    }

    public void setIs_void(boolean is_void) {
        this.is_void = is_void;
    }

    public boolean isIs_back_out() {
        return is_back_out;
    }

    public void setIs_back_out(boolean is_back_out) {
        this.is_back_out = is_back_out;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

    public boolean isIs_cut_off() {
        return is_cut_off;
    }

    public void setIs_cut_off(boolean is_cut_off) {
        this.is_cut_off = is_cut_off;
    }

    public int isCut_off_id() {
        return cut_off_id;
    }

    public void setCut_off_id(int cut_off_id) {
        this.cut_off_id = cut_off_id;
    }

    public String isCut_off_at() {
        return cut_off_at;
    }

    public void setCut_off_at(String cut_off_at) {
        this.cut_off_at = cut_off_at;
    }

    public int getCut_off_id() {
        return cut_off_id;
    }

    public String getCut_off_at() {
        return cut_off_at;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public double getVat_expense() {
        return vat_expense;
    }

    public void setVat_expense(double vat_expense) {
        this.vat_expense = vat_expense;
    }

    public boolean isIs_discount_exempt() {
        return is_discount_exempt;
    }

    public void setIs_discount_exempt(boolean is_discount_exempt) {
        this.is_discount_exempt = is_discount_exempt;
    }

    public boolean isIs_open_price() {
        return is_open_price;
    }

    public void setIs_open_price(boolean is_open_price) {
        this.is_open_price = is_open_price;
    }

    public boolean isWith_serial() {
        return with_serial;
    }

    public void setWith_serial(boolean with_serial) {
        this.with_serial = with_serial;
    }

    public boolean isIs_return() {
        return is_return;
    }

    public void setIs_return(boolean is_return) {
        this.is_return = is_return;
    }

    public boolean isIs_zero_rated() {
        return is_zero_rated;
    }

    public void setIs_zero_rated(boolean is_zero_rated) {
        this.is_zero_rated = is_zero_rated;
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
