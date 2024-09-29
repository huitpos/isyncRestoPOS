package com.example.isyncpos.response.entity;

import java.util.List;

public class AccountReceivableResponse {

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

        private int transaction_id;
        private int pos_machine_id;
        private String control_number;
        private String receipt_number;
        private double gross_sales;
        private double net_sales;
        private double vatable_sales;
        private double vat_exempt_sales;
        private double vat_amount;
        private double discount_amount;
        private double tender_amount;
        private double change;
        private double service_charge;
        private String type;
        private int cashier_id;
        private String cashier_name;
        private int take_order_id;
        private String take_order_name;
        private double total_unit_cost;
        private double total_void_amount;
        private String shift_number;
        private int is_void;
        private int void_by_id;
        private String void_by;
        private String void_at;
        private int is_back_out;
        private int is_back_out_id;
        private String back_out_by;
        private int charge_account_id;
        private String charge_account_name;
        private int is_account_receivable;
        private int is_sent_to_server;
        private int is_complete;
        private String completed_at;
        private int is_cut_off;
        private int cut_off_id;
        private String cut_off_at;
        private int branch_id;
        private String guest_name;
        private int is_resume_printed;
        private String treg;
        private String backout_at;
        private double total_quantity;
        private double total_void_qty;
        private double vat_expense;
        private int is_return;
        private double total_cash_amount;
        private double total_return_amount;
        private int void_counter;
        private String void_remarks;
        private String customer_name;
        private double total_zero_rated_amount;
        private int company_id;
        private int is_account_receivable_redeem;
        private String account_receivable_redeem_at;
        private List<OrderDataClass> items;
        private List<DiscountClassData> discounts;
        private List<DiscountOtherInformationClassData> discount_other_informations;
        private List<DiscountDetailClassData> discount_details;
        private List<PaymentClassData> payments;
        private List<PaymentOtherInformationClassData> payment_other_informations;
        private List<OfficialReceiptInformationClassData> official_receipt_informations;

        public int getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(int transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getPos_machine_id() {
            return pos_machine_id;
        }

        public void setPos_machine_id(int pos_machine_id) {
            this.pos_machine_id = pos_machine_id;
        }

        public String getControl_number() {
            return control_number;
        }

        public void setControl_number(String control_number) {
            this.control_number = control_number;
        }

        public String getReceipt_number() {
            return receipt_number;
        }

        public void setReceipt_number(String receipt_number) {
            this.receipt_number = receipt_number;
        }

        public double getGross_sales() {
            return gross_sales;
        }

        public void setGross_sales(double gross_sales) {
            this.gross_sales = gross_sales;
        }

        public double getNet_sales() {
            return net_sales;
        }

        public void setNet_sales(double net_sales) {
            this.net_sales = net_sales;
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

        public double getVat_amount() {
            return vat_amount;
        }

        public void setVat_amount(double vat_amount) {
            this.vat_amount = vat_amount;
        }

        public double getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(double discount_amount) {
            this.discount_amount = discount_amount;
        }

        public double getTender_amount() {
            return tender_amount;
        }

        public void setTender_amount(double tender_amount) {
            this.tender_amount = tender_amount;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public double getService_charge() {
            return service_charge;
        }

        public void setService_charge(double service_charge) {
            this.service_charge = service_charge;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getTake_order_id() {
            return take_order_id;
        }

        public void setTake_order_id(int take_order_id) {
            this.take_order_id = take_order_id;
        }

        public String getTake_order_name() {
            return take_order_name;
        }

        public void setTake_order_name(String take_order_name) {
            this.take_order_name = take_order_name;
        }

        public double getTotal_unit_cost() {
            return total_unit_cost;
        }

        public void setTotal_unit_cost(double total_unit_cost) {
            this.total_unit_cost = total_unit_cost;
        }

        public double getTotal_void_amount() {
            return total_void_amount;
        }

        public void setTotal_void_amount(double total_void_amount) {
            this.total_void_amount = total_void_amount;
        }

        public String getShift_number() {
            return shift_number;
        }

        public void setShift_number(String shift_number) {
            this.shift_number = shift_number;
        }

        public int getIs_void() {
            return is_void;
        }

        public void setIs_void(int is_void) {
            this.is_void = is_void;
        }

        public int getVoid_by_id() {
            return void_by_id;
        }

        public void setVoid_by_id(int void_by_id) {
            this.void_by_id = void_by_id;
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

        public int getCharge_account_id() {
            return charge_account_id;
        }

        public void setCharge_account_id(int charge_account_id) {
            this.charge_account_id = charge_account_id;
        }

        public String getCharge_account_name() {
            return charge_account_name;
        }

        public void setCharge_account_name(String charge_account_name) {
            this.charge_account_name = charge_account_name;
        }

        public int getIs_account_receivable() {
            return is_account_receivable;
        }

        public void setIs_account_receivable(int is_account_receivable) {
            this.is_account_receivable = is_account_receivable;
        }

        public int getIs_sent_to_server() {
            return is_sent_to_server;
        }

        public void setIs_sent_to_server(int is_sent_to_server) {
            this.is_sent_to_server = is_sent_to_server;
        }

        public int getIs_complete() {
            return is_complete;
        }

        public void setIs_complete(int is_complete) {
            this.is_complete = is_complete;
        }

        public String getCompleted_at() {
            return completed_at;
        }

        public void setCompleted_at(String completed_at) {
            this.completed_at = completed_at;
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

        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public String getGuest_name() {
            return guest_name;
        }

        public void setGuest_name(String guest_name) {
            this.guest_name = guest_name;
        }

        public int getIs_resume_printed() {
            return is_resume_printed;
        }

        public void setIs_resume_printed(int is_resume_printed) {
            this.is_resume_printed = is_resume_printed;
        }

        public String getTreg() {
            return treg;
        }

        public void setTreg(String treg) {
            this.treg = treg;
        }

        public String getBackout_at() {
            return backout_at;
        }

        public void setBackout_at(String backout_at) {
            this.backout_at = backout_at;
        }

        public double getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(double total_quantity) {
            this.total_quantity = total_quantity;
        }

        public double getTotal_void_qty() {
            return total_void_qty;
        }

        public void setTotal_void_qty(double total_void_qty) {
            this.total_void_qty = total_void_qty;
        }

        public double getVat_expense() {
            return vat_expense;
        }

        public void setVat_expense(double vat_expense) {
            this.vat_expense = vat_expense;
        }

        public int getIs_return() {
            return is_return;
        }

        public void setIs_return(int is_return) {
            this.is_return = is_return;
        }

        public double getTotal_cash_amount() {
            return total_cash_amount;
        }

        public void setTotal_cash_amount(double total_cash_amount) {
            this.total_cash_amount = total_cash_amount;
        }

        public double getTotal_return_amount() {
            return total_return_amount;
        }

        public void setTotal_return_amount(double total_return_amount) {
            this.total_return_amount = total_return_amount;
        }

        public int getVoid_counter() {
            return void_counter;
        }

        public void setVoid_counter(int void_counter) {
            this.void_counter = void_counter;
        }

        public String getVoid_remarks() {
            return void_remarks;
        }

        public void setVoid_remarks(String void_remarks) {
            this.void_remarks = void_remarks;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
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

        public int getIs_account_receivable_redeem() {
            return is_account_receivable_redeem;
        }

        public void setIs_account_receivable_redeem(int is_account_receivable_redeem) {
            this.is_account_receivable_redeem = is_account_receivable_redeem;
        }

        public String getAccount_receivable_redeem_at() {
            return account_receivable_redeem_at;
        }

        public void setAccount_receivable_redeem_at(String account_receivable_redeem_at) {
            this.account_receivable_redeem_at = account_receivable_redeem_at;
        }

        public List<OrderDataClass> getItems() {
            return items;
        }

        public void setItems(List<OrderDataClass> items) {
            this.items = items;
        }

        public List<DiscountClassData> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<DiscountClassData> discounts) {
            this.discounts = discounts;
        }

        public List<DiscountOtherInformationClassData> getDiscount_other_informations() {
            return discount_other_informations;
        }

        public void setDiscount_other_informations(List<DiscountOtherInformationClassData> discount_other_informations) {
            this.discount_other_informations = discount_other_informations;
        }

        public List<DiscountDetailClassData> getDiscount_details() {
            return discount_details;
        }

        public void setDiscount_details(List<DiscountDetailClassData> discount_details) {
            this.discount_details = discount_details;
        }

        public List<PaymentClassData> getPayments() {
            return payments;
        }

        public void setPayments(List<PaymentClassData> payments) {
            this.payments = payments;
        }

        public List<PaymentOtherInformationClassData> getPayment_other_informations() {
            return payment_other_informations;
        }

        public void setPayment_other_informations(List<PaymentOtherInformationClassData> payment_other_informations) {
            this.payment_other_informations = payment_other_informations;
        }

        public List<OfficialReceiptInformationClassData> getOfficial_receipt_informations() {
            return official_receipt_informations;
        }

        public void setOfficial_receipt_informations(List<OfficialReceiptInformationClassData> official_receipt_informations) {
            this.official_receipt_informations = official_receipt_informations;
        }

        public class OrderDataClass {

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

        public class DiscountClassData {

            private int discount_id;
            private int pos_machine_id;
            private int branch_id;
            private int transaction_id;
            private int custom_discount_id;
            private int discount_type_id;
            private String discount_name;
            private double value;
            private double discount_amount;
            private double vat_exempt_amount;
            private String type;
            private int cashier_id;
            private String cashier_name;
            private int authorize_id;
            private String authorize_name;
            private int is_void;
            private int void_by_id;
            private String void_by;
            private String void_at;
            private int is_sent_to_server;
            private int is_zero_rated;
            private int is_cut_off;
            private int cut_off_id;
            private int shift_number;
            private String treg;
            private double vat_expense;
            private double gross_amount;
            private double net_amount;
            private int company_id;

            public int getDiscount_id() {
                return discount_id;
            }

            public void setDiscount_id(int discount_id) {
                this.discount_id = discount_id;
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

            public int getCustom_discount_id() {
                return custom_discount_id;
            }

            public void setCustom_discount_id(int custom_discount_id) {
                this.custom_discount_id = custom_discount_id;
            }

            public int getDiscount_type_id() {
                return discount_type_id;
            }

            public void setDiscount_type_id(int discount_type_id) {
                this.discount_type_id = discount_type_id;
            }

            public String getDiscount_name() {
                return discount_name;
            }

            public void setDiscount_name(String discount_name) {
                this.discount_name = discount_name;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public double getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(double discount_amount) {
                this.discount_amount = discount_amount;
            }

            public double getVat_exempt_amount() {
                return vat_exempt_amount;
            }

            public void setVat_exempt_amount(double vat_exempt_amount) {
                this.vat_exempt_amount = vat_exempt_amount;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public int getIs_void() {
                return is_void;
            }

            public void setIs_void(int is_void) {
                this.is_void = is_void;
            }

            public int getVoid_by_id() {
                return void_by_id;
            }

            public void setVoid_by_id(int void_by_id) {
                this.void_by_id = void_by_id;
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

            public int getIs_sent_to_server() {
                return is_sent_to_server;
            }

            public void setIs_sent_to_server(int is_sent_to_server) {
                this.is_sent_to_server = is_sent_to_server;
            }

            public int getIs_zero_rated() {
                return is_zero_rated;
            }

            public void setIs_zero_rated(int is_zero_rated) {
                this.is_zero_rated = is_zero_rated;
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

            public double getVat_expense() {
                return vat_expense;
            }

            public void setVat_expense(double vat_expense) {
                this.vat_expense = vat_expense;
            }

            public double getGross_amount() {
                return gross_amount;
            }

            public void setGross_amount(double gross_amount) {
                this.gross_amount = gross_amount;
            }

            public double getNet_amount() {
                return net_amount;
            }

            public void setNet_amount(double net_amount) {
                this.net_amount = net_amount;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

        }

        public class DiscountOtherInformationClassData {

            private int device_id;
            private int discount_other_information_id;
            private int pos_machine_id;
            private int branch_id;
            private int transaction_id;
            private int discount_id;
            private String name;
            private String value;
            private int is_cut_off;
            private int cut_off_id;
            private int is_void;
            private int is_sent_to_server;
            private String treg;
            private int company_id;

            public int getDevice_id() {
                return device_id;
            }

            public void setDevice_id(int device_id) {
                this.device_id = device_id;
            }

            public int getDiscount_other_information_id() {
                return discount_other_information_id;
            }

            public void setDiscount_other_information_id(int discount_other_information_id) {
                this.discount_other_information_id = discount_other_information_id;
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

            public int getDiscount_id() {
                return discount_id;
            }

            public void setDiscount_id(int discount_id) {
                this.discount_id = discount_id;
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

            public int getIs_void() {
                return is_void;
            }

            public void setIs_void(int is_void) {
                this.is_void = is_void;
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

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

        }

        public class DiscountDetailClassData {

            private int discount_details_id;
            private int discount_id;
            private int pos_machine_id;
            private int branch_id;
            private int custom_discount_id;
            private int transaction_id;
            private int order_id;
            private int discount_type_id;
            private double value;
            private double discount_amount;
            private double vat_exempt_amount;
            private String type;
            private int is_void;
            private int void_by_id;
            private String void_by;
            private String void_at;
            private int is_sent_to_server;
            private int is_cut_off;
            private int cut_off_id;
            private int is_vat_exempt;
            private int is_zero_rated;
            private int shift_number;
            private String treg;
            private double vat_expense;
            private int company_id;

            public int getDiscount_details_id() {
                return discount_details_id;
            }

            public void setDiscount_details_id(int discount_details_id) {
                this.discount_details_id = discount_details_id;
            }

            public int getDiscount_id() {
                return discount_id;
            }

            public void setDiscount_id(int discount_id) {
                this.discount_id = discount_id;
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

            public int getCustom_discount_id() {
                return custom_discount_id;
            }

            public void setCustom_discount_id(int custom_discount_id) {
                this.custom_discount_id = custom_discount_id;
            }

            public int getTransaction_id() {
                return transaction_id;
            }

            public void setTransaction_id(int transaction_id) {
                this.transaction_id = transaction_id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getDiscount_type_id() {
                return discount_type_id;
            }

            public void setDiscount_type_id(int discount_type_id) {
                this.discount_type_id = discount_type_id;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public double getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(double discount_amount) {
                this.discount_amount = discount_amount;
            }

            public double getVat_exempt_amount() {
                return vat_exempt_amount;
            }

            public void setVat_exempt_amount(double vat_exempt_amount) {
                this.vat_exempt_amount = vat_exempt_amount;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIs_void() {
                return is_void;
            }

            public void setIs_void(int is_void) {
                this.is_void = is_void;
            }

            public int getVoid_by_id() {
                return void_by_id;
            }

            public void setVoid_by_id(int void_by_id) {
                this.void_by_id = void_by_id;
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

            public int getIs_vat_exempt() {
                return is_vat_exempt;
            }

            public void setIs_vat_exempt(int is_vat_exempt) {
                this.is_vat_exempt = is_vat_exempt;
            }

            public int getIs_zero_rated() {
                return is_zero_rated;
            }

            public void setIs_zero_rated(int is_zero_rated) {
                this.is_zero_rated = is_zero_rated;
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

            public double getVat_expense() {
                return vat_expense;
            }

            public void setVat_expense(double vat_expense) {
                this.vat_expense = vat_expense;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

        }

        public class PaymentClassData {

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

        public class PaymentOtherInformationClassData {

            private int device_id;
            private int payment_other_information_id;
            private int pos_machine_id;
            private int branch_id;
            private int transaction_id;
            private int payment_id;
            private String name;
            private String value;
            private int is_cut_off;
            private int cut_off_id;
            private int is_void;
            private int is_sent_to_server;
            private String treg;
            private int company_id;

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

            public int getIs_void() {
                return is_void;
            }

            public void setIs_void(int is_void) {
                this.is_void = is_void;
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

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

        }

        public class OfficialReceiptInformationClassData {

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

    }

}
