package com.example.isyncpos.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.isyncpos.POSApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Writer {

    private static Writer instance;
    private Dates date;

    public synchronized static Writer getInstance(){
        if(instance == null){
            instance = new Writer();
        }
        return instance;
    }

    private String removePrinterCode(String content){
        //Replace The String
        content = content.replace("[L]", "");
        content = content.replace("[R]", "  ");
        content = content.replace("[C]", "  ");
        content = content.replace("<b>", "");
        content = content.replace("</b>", "");
        return content;
    }

    public void writeTransaction(Context context, String content){
        date = new Dates();
        //Get Date Time
        String dateNow = date.nowDateOnly(null);
        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory(), "Documents/ISyncPOS/" + dateNow + ".txt");
                if (!file.exists()){
                    try {
                        File root = new File(Environment.getExternalStorageDirectory(), "Documents/ISyncPOS");
                        if(!root.exists()) root.mkdirs();
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(root, dateNow + ".txt"));
                        fileOutputStream.write(removePrinterCode(content).getBytes());
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    try {
                        File root = new File(Environment.getExternalStorageDirectory(), "Documents/ISyncPOS");
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream((new File(root, dateNow + ".txt")), true));
                        outputStreamWriter.append(removePrinterCode(content));
                        outputStreamWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        executorService.shutdown();
    }

}
