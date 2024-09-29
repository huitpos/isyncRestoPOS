package com.example.isyncpos.response.entity;

import java.util.List;

public class EndOfDayResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public class DataClass {

        private int device_id;
        private int end_of_day_id;
        private int pos_machine_id;
        private int branch_id;
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
        private double void_amount;
        private double total_change;
        private double total_payout;
        private double total_service_charge;
        private double total_discount_amount;
        private double total_cost;
        private double total_sk;
        private int cashier_id;
        private String cashier_name;
        private int admin_id;
        private String admin_name;
        private int shift_number;
        private int is_sent_to_server;
        private String treg;
        private int reading_number;
        private int void_qty;
        private double total_short_over;
        private String generated_date;
        private double total_zero_rated_amount;
        private int beg_reading_number;
        private int end_reading_number;
        private String print_string;
        private int company_id;
        private List<DepartmentsClass> departments;
        private List<DiscountsClass> discounts;
        private List<PaymentsClass> payments;
        private double total_return;

        public class DepartmentsClass {

            private int end_of_day_department_id;
            private int pos_machine_id;
            private int branch_id;
            private int end_of_day_id;
            private int department_id;
            private String name;
            private double transaction_count;
            private double amount;
            private int is_sent_to_server;
            private String treg;
            private int company_id;

            public int getEnd_of_day_department_id() {
                return end_of_day_department_id;
            }

            public void setEnd_of_day_department_id(int end_of_day_department_id) {
                this.end_of_day_department_id = end_of_day_department_id;
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

            public int getEnd_of_day_id() {
                return end_of_day_id;
            }

            public void setEnd_of_day_id(int end_of_day_id) {
                this.end_of_day_id = end_of_day_id;
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

            public double getTransaction_count() {
                return transaction_count;
            }

            public void setTransaction_count(double transaction_count) {
                this.transaction_count = transaction_count;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int isIs_sent_to_server() {
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

        public class DiscountsClass {

            private int end_of_day_discount_id;
            private int pos_machine_id;
            private int branch_id;
            private int end_of_day_id;
            private int discount_type_id;
            private String name;
            private int transaction_count;
            private double amount;
            private int is_sent_to_server;
            private String treg;
            private int company_id;
            private int is_zero_rated;

            public int getEnd_of_day_discount_id() {
                return end_of_day_discount_id;
            }

            public void setEnd_of_day_discount_id(int end_of_day_discount_id) {
                this.end_of_day_discount_id = end_of_day_discount_id;
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

            public int getEnd_of_day_id() {
                return end_of_day_id;
            }

            public void setEnd_of_day_id(int end_of_day_id) {
                this.end_of_day_id = end_of_day_id;
            }

            public int getDiscount_type_id() {
                return discount_type_id;
            }

            public void setDiscount_type_id(int discount_type_id) {
                this.discount_type_id = discount_type_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTransaction_count() {
                return transaction_count;
            }

            public void setTransaction_count(int transaction_count) {
                this.transaction_count = transaction_count;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
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

            public int getIs_zero_rated() {
                return is_zero_rated;
            }

            public void setIs_zero_rated(int is_zero_rated) {
                this.is_zero_rated = is_zero_rated;
            }

        }

        public class PaymentsClass {

            private int end_of_day_payment_id;
            private int pos_machine_id;
            private int branch_id;
            private int end_of_day_id;
            private int payment_type_id;
            private String name;
            private int transaction_count;
            private double amount;
            private int is_sent_to_server;
            private String treg;
            private int company_id;

            public int getEnd_of_day_payment_id() {
                return end_of_day_payment_id;
            }

            public void setEnd_of_day_payment_id(int end_of_day_payment_id) {
                this.end_of_day_payment_id = end_of_day_payment_id;
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

            public int getEnd_of_day_id() {
                return end_of_day_id;
            }

            public void setEnd_of_day_id(int end_of_day_id) {
                this.end_of_day_id = end_of_day_id;
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

            public int getTransaction_count() {
                return transaction_count;
            }

            public void setTransaction_count(int transaction_count) {
                this.transaction_count = transaction_count;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
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

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public int getEnd_of_day_id() {
            return end_of_day_id;
        }

        public void setEnd_of_day_id(int end_of_day_id) {
            this.end_of_day_id = end_of_day_id;
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

        public double getTotal_sk() {
            return total_sk;
        }

        public void setTotal_sk(double total_sk) {
            this.total_sk = total_sk;
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

        public int getReading_number() {
            return reading_number;
        }

        public void setReading_number(int reading_number) {
            this.reading_number = reading_number;
        }

        public int getVoid_qty() {
            return void_qty;
        }

        public void setVoid_qty(int void_qty) {
            this.void_qty = void_qty;
        }

        public double getTotal_short_over() {
            return total_short_over;
        }

        public void setTotal_short_over(double total_short_over) {
            this.total_short_over = total_short_over;
        }

        public String getGenerated_date() {
            return generated_date;
        }

        public void setGenerated_date(String generated_date) {
            this.generated_date = generated_date;
        }

        public double getTotal_zero_rated_amount() {
            return total_zero_rated_amount;
        }

        public void setTotal_zero_rated_amount(double total_zero_rated_amount) {
            this.total_zero_rated_amount = total_zero_rated_amount;
        }

        public int getBeg_reading_number() {
            return beg_reading_number;
        }

        public void setBeg_reading_number(int beg_reading_number) {
            this.beg_reading_number = beg_reading_number;
        }

        public int getEnd_reading_number() {
            return end_reading_number;
        }

        public void setEnd_reading_number(int end_reading_number) {
            this.end_reading_number = end_reading_number;
        }

        public String getPrint_string() {
            return print_string;
        }

        public void setPrint_string(String print_string) {
            this.print_string = print_string;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public List<DepartmentsClass> getDepartments() {
            return departments;
        }

        public void setDepartments(List<DepartmentsClass> departments) {
            this.departments = departments;
        }

        public List<DiscountsClass> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<DiscountsClass> discounts) {
            this.discounts = discounts;
        }

        public List<PaymentsClass> getPayments() {
            return payments;
        }

        public void setPayments(List<PaymentsClass> payments) {
            this.payments = payments;
        }

        public double getTotal_return() {
            return total_return;
        }

        public void setTotal_return(double total_return) {
            this.total_return = total_return;
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
