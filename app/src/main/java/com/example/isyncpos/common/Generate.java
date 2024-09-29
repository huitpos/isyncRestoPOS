package com.example.isyncpos.common;

import android.app.Activity;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.util.Date;

public class Generate {

    private String paddingControlNumber = "00000000000000000000";
    private String paddingOR = "000000000000";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private DecimalFormat decimalFormat = new DecimalFormat();

    public Generate(){
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
    }

    public String controlNumber(){
        return paddingControlNumber.substring(simpleDateFormat.format(new Date()).toString().length()) + simpleDateFormat.format(new Date()).toString();
    }

    public double toFourDecimal(double value){
        return Double.parseDouble(String.format("%.4f", value));
    }

    public String toTwoDecimalWithComma(double value){
        decimalFormat.applyPattern("#,##0.00");
        return decimalFormat.format(value);
    }

    public double toTwoDecimal(double value){
        decimalFormat.applyPattern("###0.00");
        return Double.parseDouble(decimalFormat.format(value));
    }

    public String backupTimestamp(){
        return simpleDateFormat.format(new Date()).toString();
    }

    public String officialReceipt(int orCounter){
        return paddingOR.substring(String.valueOf(orCounter).length()) + orCounter;
    }

}
