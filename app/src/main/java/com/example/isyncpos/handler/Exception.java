package com.example.isyncpos.handler;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.example.isyncpos.common.Dates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Exception implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final Context context;

    public Exception(Context context){
        this.context = context;
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        // Log the exception to a file
        logToFile(throwable);

        // Call the default exception handler
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    }

    private void logToFile(Throwable throwable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){
                Dates date = new Dates();
                String dateNow = date.nowDateOnly(null);
                File file = new File(Environment.getExternalStorageDirectory(), "Documents/ISyncPOSError/" + dateNow + "ErrorLog.txt");
                if(!file.exists()){
                    File root = new File(Environment.getExternalStorageDirectory(), "Documents/ISyncPOSError");
                    if(!root.exists()) root.mkdirs();
                    try (FileOutputStream stream = new FileOutputStream(file, true);
                         PrintWriter writer = new PrintWriter(stream)) {
                        writer.println("Exception: " + throwable.toString());
                        writer.println("Stack Trace: ");
                        for (StackTraceElement element : throwable.getStackTrace()) {
                            writer.println("    at " + element.toString());
                        }
                        writer.println("-----------------------------------------------------");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try (FileOutputStream stream = new FileOutputStream(file, true);
                         PrintWriter writer = new PrintWriter(stream)) {
                        writer.println("Exception: " + throwable.toString());
                        writer.println("Stack Trace: ");
                        for (StackTraceElement element : throwable.getStackTrace()) {
                            writer.println("    at " + element.toString());
                        }
                        writer.println("-----------------------------------------------------");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
