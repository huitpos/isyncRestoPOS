package com.example.isyncpos.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.LoginActivity;
import com.example.isyncpos.handler.POSDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.raphaelebner.roomdatabasebackup.core.OnCompleteListener;
import de.raphaelebner.roomdatabasebackup.core.RoomBackup;

public class Backup {

    private Activity activity;
    private Context context;
    private RoomBackup roomBackup;
    private Generate generate;
    private POSDatabase posDatabase;

    public Backup(Activity activity, Context context){
        this.roomBackup = new RoomBackup(context);
        this.context = context;
        this.activity = activity;
        generate = new Generate();
        posDatabase = POSDatabase.getInstance(context);
    }

    public void processBackupDatabase(){
        LoadingDialog.getInstance().startLoadingDialog(activity, "Backup on progress please wait.....");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //This will write all the data on the database (FOR WAL MODE)
                posDatabase.getOpenHelper().getWritableDatabase().query("PRAGMA wal_checkpoint(FULL)");

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + BuildConfig.APP_DATABASE + generate.backupTimestamp() + ".sqlite3");
                        roomBackup.database(POSDatabase.getInstance(roomBackup.getContext()));
                        roomBackup.enableLogDebug(true);
                        roomBackup.backupLocationCustomFile(file);
                        roomBackup.maxFileCount(5);
                        roomBackup.backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_FILE);
                        roomBackup.onCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(boolean success, String message, int code) {
                                if(success){
                                    LoadingDialog.getInstance().changeLoadingMessage("Backup process is done restarting the application now...");
                                    final Handler handlerRestart = new Handler();
                                    handlerRestart.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            roomBackup.restartApp(new Intent(activity.getApplicationContext(), LoginActivity.class));
                                        }
                                    }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                                }
                                else{
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    Toast.makeText(context, "Backup process is failed.", Toast.LENGTH_LONG).show();
                                }
                                Log.d("BACKUP LOG", "success: " + success + ", message: " + message + ", exitCode: " + code);
                            }
                        });
                        final Handler handlerBackup = new Handler();
                        handlerBackup.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    roomBackup.backup();
                                }
                                catch (Exception e){
                                    Log.d("ROOMBACKUP", "ROOMBACKUP: " + e.getCause());
                                    //This will go to the normal backup if the room database backup failed
                                    normalBackUp();
                                }
                            }
                        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                    }
                });

            }
        });
    }

    public void processRestoreDatabase(Uri uri) throws IOException {

        // Copy the selected file to internal storage
        final String DB_PATH = context.getDatabasePath(BuildConfig.APP_DATABASE).getAbsolutePath();
        File dbFile = new File(DB_PATH);

        // Close the existing Room database
        POSDatabase.getInstance(context).close();

        try (ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
             FileInputStream inputStream = new FileInputStream(pfd.getFileDescriptor());
             FileOutputStream outputStream = new FileOutputStream(dbFile)) {

            copyFile(inputStream, outputStream);
        }

    }

    public void restartApplication(){
        roomBackup.restartApp(new Intent(activity.getApplicationContext(), LoginActivity.class));
    }

    private void copyFile(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
    }

    private void normalBackUp(){

        //Close The Database Connection
        posDatabase.close();

        final String DB_PATH = context.getDatabasePath(BuildConfig.APP_DATABASE).getAbsolutePath();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                File dbFile = new File(DB_PATH);
                File backupFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + BuildConfig.APP_DATABASE + generate.backupTimestamp() + ".sqlite3");

                try {
                    FileInputStream fis = new FileInputStream(dbFile);
                    FileOutputStream fos = new FileOutputStream(backupFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                    fis.close();
                    fos.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().changeLoadingMessage("Backup process is done restarting the application now...");
                        final Handler handlerRestart = new Handler();
                        handlerRestart.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                roomBackup.restartApp(new Intent(activity.getApplicationContext(), LoginActivity.class));
                            }
                        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                    }
                });

            }
        });

    }

}
