package com.example.isyncpos.common;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.LocaleData;
import android.icu.util.TimeZone;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Dates {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateAMPMFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
    private SimpleDateFormat parseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Dates() {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
        parseDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
        simpleDateAMPMFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
    }

    public String now(){
        return simpleDateFormat.format(new Date()).toString();
    }

    public String timeNow(String date){
        if(date == null){
            return simpleDateFormat.format(new Date()).split(" ")[1].toString() + " " + simpleDateFormat.format(new Date()).split(" ")[2].toString();
        }
        else{
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                return simpleDateAMPMFormat.format(d).split(" ")[1].toString() + " " + simpleDateAMPMFormat.format(d).split(" ")[2].toString();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String nowDateOnly(String date){
        if(date == null){
            return parseDateFormat.format(new Date()).toString();
        }
        else{
            try {
                return parseDateFormat.format(parseDateFormat.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String nowDateOnlyAddYear(String date, int amount){
        if(date == null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR, amount);
            return parseDateFormat.format(calendar.getTime());
        }
        else{
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                calendar.setTime(parseDateFormat.parse(date));
                calendar.add(Calendar.YEAR, amount);
                return parseDateFormat.format(calendar.getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Date parseToDate(String datetime){
        try {
            return simpleDateFormat.parse(datetime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
