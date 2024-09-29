package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.SyncAdapter;
import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.viewmodel.SyncViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class SyncDialog extends DialogFragment {

    private AlertDialog alertDialog;
    private View view;
    private MaterialButton btnSyncPositive;
    private SyncViewModel syncViewModel;
    private RecyclerView recyclerSync;
    private SyncAdapter syncAdapter;
    private UsersViewModel usersViewModel;

    public static SyncDialog newSyncDialog(){
        return new SyncDialog();
    }

    public void setArgs(SyncViewModel syncViewModel, UsersViewModel usersViewModel){
        this.syncViewModel = syncViewModel;
        this.usersViewModel = usersViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_sync, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        initialize();

        syncViewModel.fetchAll().observe(getActivity(), new Observer<List<Sync>>() {
            @Override
            public void onChanged(List<Sync> syncs) {
                syncAdapter.submitList(syncs);
            }
        });

        btnSyncPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Sync> syncList = syncViewModel.fetchUnfinish();
                    if(syncList.size() == 0){
                        List<Users> users = usersViewModel.fetchAll();
                        if(users.size() != 0){
                            dismiss();
                        }
                        else{
                            Toast.makeText(getContext(), "There is no user data has been sync please put a user to the branch before we can proceed.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Please wait for all of the information are sync.", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alertDialog = builder.create();
        return alertDialog;
    }

    private void initialize(){
        btnSyncPositive = view.findViewById(R.id.btnSyncPositive);
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerSync = view.findViewById(R.id.recyclerSync);
        recyclerSync.setHasFixedSize(true);
        recyclerSync.setLayoutManager(new LinearLayoutManager(getContext()));
        syncAdapter = new SyncAdapter(getContext());
        recyclerSync.setAdapter(syncAdapter);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        syncViewModel.fetchAll().removeObservers(this);
    }

}
