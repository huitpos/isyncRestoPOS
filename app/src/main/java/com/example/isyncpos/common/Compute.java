package com.example.isyncpos.common;

import android.util.Log;

public class Compute {

    private double vatable = 1.12;
    private double vatAmount = 0.12;

    public double gross(double qty, double price){
        double value = qty * price;
        return value;
    }

    public double total(double total, double qty, double price){
        double value = total + (qty * price);
        return value;
    }

    public double vatableSales(double grossSales){
        double value = grossSales / vatable;
        return value;
    }

    public double vatExempt(double grossSales){
        double value = grossSales / vatable;
        return value;
    }

    public double discountAmount(double vatExempt, double discountValue, String type){
        return 0;
    }

    public double vatAmount(double vatableSales){
        double value = vatableSales * vatAmount;
        return value;
    }

    public double toPercentage(double amount){
        double value = amount / 100;
        return value;
    }

}
