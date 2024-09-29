package com.example.isyncpos.response.entity;

import java.util.List;

public class SpotAuditResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int id;
        private int spot_audit_id;
        private int pos_machine_id;
        private int branch_id;
        private int company_id;
        private String beginning_or;
        private String ending_or;
        private double beginning_amount;
        private double ending_amount;
        private int total_transactions;
        private double gross_sales;
        private double net_sales;
        private double vatable_sales;
        private double vat_exempt_sales;
        private double vat_amount;
        private double vat_expense;
        private int void_qty;
        private double void_amount;
        private double total_change;
        private double total_payout;
        private double total_service_charge;
        private double total_discount_amount;
        private double total_cost;
        private double safekeeping_amount;
        private double safekeeping_short_over;
        private double total_sk;
        private double total_short_over;
        private int cashier_id;
        private String cashier_name;
        private int admin_id;
        private String admin_name;
        private int shift_number;
        private int is_cut_off;
        private int cut_off_id;
        private int is_sent_to_server;
        private String treg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSpot_audit_id() {
            return spot_audit_id;
        }

        public void setSpot_audit_id(int spot_audit_id) {
            this.spot_audit_id = spot_audit_id;
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

        public String getBeginning_or() {
            return beginning_or;
        }

        public void setBeginning_or(String beginning_or) {
            this.beginning_or = beginning_or;
        }

        public String getEnding_or() {
            return ending_or;
        }

        public void setEnding_or(String ending_or) {
            this.ending_or = ending_or;
        }

        public double getBeginning_amount() {
            return beginning_amount;
        }

        public void setBeginning_amount(double beginning_amount) {
            this.beginning_amount = beginning_amount;
        }

        public double getEnding_amount() {
            return ending_amount;
        }

        public void setEnding_amount(double ending_amount) {
            this.ending_amount = ending_amount;
        }

        public int getTotal_transactions() {
            return total_transactions;
        }

        public void setTotal_transactions(int total_transactions) {
            this.total_transactions = total_transactions;
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

        public double getVat_expense() {
            return vat_expense;
        }

        public void setVat_expense(double vat_expense) {
            this.vat_expense = vat_expense;
        }

        public int getVoid_qty() {
            return void_qty;
        }

        public void setVoid_qty(int void_qty) {
            this.void_qty = void_qty;
        }

        public double getVoid_amount() {
            return void_amount;
        }

        public void setVoid_amount(double void_amount) {
            this.void_amount = void_amount;
        }

        public double getTotal_change() {
            return total_change;
        }

        public void setTotal_change(double total_change) {
            this.total_change = total_change;
        }

        public double getTotal_payout() {
            return total_payout;
        }

        public void setTotal_payout(double total_payout) {
            this.total_payout = total_payout;
        }

        public double getTotal_service_charge() {
            return total_service_charge;
        }

        public void setTotal_service_charge(double total_service_charge) {
            this.total_service_charge = total_service_charge;
        }

        public double getTotal_discount_amount() {
            return total_discount_amount;
        }

        public void setTotal_discount_amount(double total_discount_amount) {
            this.total_discount_amount = total_discount_amount;
        }

        public double getTotal_cost() {
            return total_cost;
        }

        public void setTotal_cost(double total_cost) {
            this.total_cost = total_cost;
        }

        public double getSafekeeping_amount() {
            return safekeeping_amount;
        }

        public void setSafekeeping_amount(double safekeeping_amount) {
            this.safekeeping_amount = safekeeping_amount;
        }

        public double getSafekeeping_short_over() {
            return safekeeping_short_over;
        }

        public void setSafekeeping_short_over(double safekeeping_short_over) {
            this.safekeeping_short_over = safekeeping_short_over;
        }

        public double getTotal_sk() {
            return total_sk;
        }

        public void setTotal_sk(double total_sk) {
            this.total_sk = total_sk;
        }

        public double getTotal_short_over() {
            return total_short_over;
        }

        public void setTotal_short_over(double total_short_over) {
            this.total_short_over = total_short_over;
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

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
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
