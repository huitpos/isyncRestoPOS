package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.ResumeTransactionAdapter;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ResumeTransactionDialog extends DialogFragment {

    private TransactionsViewModel transactionsViewModel;
    private AlertDialog alertDialog;
    private View view;
    private MaterialButton btnResumeNegative;
    private RecyclerView recyclerResumeTransaction;
    private ResumeTransactionAdapter resumeTransactionAdapter;

    public static ResumeTransactionDialog newResumeTransactionDialog(){
        return new ResumeTransactionDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_resume_transaction,null);
        builder.setView(view);
        alertDialog = builder.create();

        //Initialize
        initialize();

        transactionsViewModel.fetchResumeTransactions().observe(getActivity(), new Observer<List<Transactions>>() {
            @Override
            public void onChanged(List<Transactions> transactions) {
                resumeTransactionAdapter.submitList(transactions);
            }
        });

        btnResumeNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return alertDialog;
    }

    private void initialize(){
        initViewModels();
        btnResumeNegative = view.findViewById(R.id.btnResumeNegative);
        recyclerResumeTransaction = view.findViewById(R.id.recyclerResumeTransaction);
        initRecyclerView();
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
    }

    private void initRecyclerView(){
        recyclerResumeTransaction = view.findViewById(R.id.recyclerResumeTransaction);
        recyclerResumeTransaction.setHasFixedSize(true);
        recyclerResumeTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        resumeTransactionAdapter = new ResumeTransactionAdapter(getActivity());
        resumeTransactionAdapter.setAlertDialog(alertDialog);
        recyclerResumeTransaction.setAdapter(resumeTransactionAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        transactionsViewModel.fetchResumeTransactions().removeObservers(getActivity());
    }
}
