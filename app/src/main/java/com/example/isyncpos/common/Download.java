package com.example.isyncpos.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.isyncpos.POSApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Download {

    private static Download instance;

    public synchronized static Download getInstance(){
        if(instance == null){
            instance = new Download();
        }
        return instance;
    }

    public void downloadFile(String fileURL, String type){
        if(type.equals("Images")) createImageDirectory();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(fileURL);
                    InputStream inputStream = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    saveImageToDownloadFolder(fileURL, bitmap);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void saveImageToDownloadFolder(String fileURL, Bitmap bitmap){
        try {
            String filename = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            File file = new File(Environment.getExternalStorageDirectory(), "Documents/images/" + filename);
            if(file.exists()){
                file.delete();
            }
            OutputStream outputStream = new FileOutputStream(file);
            if(outputStream != null && bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.d("saveImageToDownloadFolder", "saveImageToDownloadFolder: " + file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createImageDirectory(){
        File folder = new File(Environment.getExternalStorageDirectory(), "Documents/images");
        if(!folder.exists()){
            folder.mkdir();
        }
    }

}
