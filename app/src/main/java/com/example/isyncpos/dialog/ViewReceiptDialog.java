package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.ReceiptsAdapter;
import com.example.isyncpos.adapters.XReadingAdapter;
import com.example.isyncpos.adapters.ZReadingAdapter;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ViewReceiptDialog extends DialogFragment {

    private RecyclerView recyclerViewReceipt, recyclerViewReceiptCutOff, recyclerViewXReading, recyclerViewZReading;
    private ReceiptsAdapter receiptsAdapter;
    private ReceiptsAdapter receiptsAdapterCutOff;
    private XReadingAdapter xReadingAdapter;
    private ZReadingAdapter zReadingAdapter;
    private MaterialButton btnReceiptNegative;
    private AlertDialog alertDialog;
    private View view;
    private Spinner spinnerReceiptType;
    private TransactionsViewModel transactionsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private String[] types = new String[]{"RECEIPT", "CUTOFF RECEIPT", "X READING", "Z READING"};
    ArrayAdapter<String> arrayTypeAdapter;

    public static ViewReceiptDialog newViewReceiptDialog(){
        return new ViewReceiptDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_receipt,null);
        builder.setView(view);
        alertDialog = builder.create();
        //This will set the focus of the touch keyboard of the android to this dialog order to use on the recycler view edittext
//        alertDialog.setOnShowListener(dialogInterface -> alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM));
        //Initialize
        initialize();

        spinnerReceiptType.setAdapter(arrayTypeAdapter);

        transactionsViewModel.fetchCompleteTransactions().observe(getActivity(), new Observer<List<Transactions>>() {
            @Override
            public void onChanged(List<Transactions> transactions) {
                receiptsAdapter.submitList(transactions);
            }
        });

        transactionsViewModel.fetchCompleteTransactionsCutOff().observe(getActivity(), new Observer<List<Transactions>>() {
            @Override
            public void onChanged(List<Transactions> transactions) {
                receiptsAdapterCutOff.submitList(transactions);
            }
        });

        cutOffViewModel.fetchCutOffForTodayToReprint().observe(getActivity(), new Observer<List<CutOff>>() {
            @Override
            public void onChanged(List<CutOff> cutOffs) {
                xReadingAdapter.submitList(cutOffs);
            }
        });

        endOfDayViewModel.fetchEndOfDayForTodayToReprint().observe(getActivity(), new Observer<List<EndOfDay>>() {
            @Override
            public void onChanged(List<EndOfDay> endOfDays) {
                zReadingAdapter.submitList(endOfDays);
            }
        });

        spinnerReceiptType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getSelectedItem().toString();
                viewGone();
                if(type.equals("RECEIPT")){
                    recyclerViewReceipt.setVisibility(View.VISIBLE);
                } else if (type.equals("CUTOFF RECEIPT")) {
                    recyclerViewReceiptCutOff.setVisibility(View.VISIBLE);
                } else if (type.equals("X READING")) {
                    recyclerViewXReading.setVisibility(View.VISIBLE);
                } else if (type.equals("Z READING")) {
                    recyclerViewZReading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnReceiptNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return alertDialog;
    }

    private void initialize(){
        initViewModel();
        arrayTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        btnReceiptNegative = view.findViewById(R.id.btnReceiptNegative);
        spinnerReceiptType = view.findViewById(R.id.spinnerReceiptType);
        initRecyclerView();
    }

    private void viewGone(){
        recyclerViewReceipt.setVisibility(View.INVISIBLE);
        recyclerViewReceiptCutOff.setVisibility(View.INVISIBLE);
        recyclerViewXReading.setVisibility(View.INVISIBLE);
        recyclerViewZReading.setVisibility(View.INVISIBLE);
    }

    private void initRecyclerView(){
        //1
        recyclerViewReceipt = view.findViewById(R.id.recyclerViewReceipt);
        recyclerViewReceipt.setHasFixedSize(true);
        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        receiptsAdapter = new ReceiptsAdapter(getActivity(), "RECEIPT");
        recyclerViewReceipt.setAdapter(receiptsAdapter);
        //2
        recyclerViewReceiptCutOff = view.findViewById(R.id.recyclerViewReceiptCutOff);
        recyclerViewReceiptCutOff.setHasFixedSize(true);
        recyclerViewReceiptCutOff.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        receiptsAdapterCutOff = new ReceiptsAdapter(getActivity(), "CUTOFF RECEIPT");
        recyclerViewReceiptCutOff.setAdapter(receiptsAdapterCutOff);
        //3
        recyclerViewXReading = view.findViewById(R.id.recyclerViewXReading);
        recyclerViewXReading.setHasFixedSize(true);
        recyclerViewXReading.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        xReadingAdapter = new XReadingAdapter(getActivity());
        recyclerViewXReading.setAdapter(xReadingAdapter);
        //4
        recyclerViewZReading = view.findViewById(R.id.recyclerViewZReading);
        recyclerViewZReading.setHasFixedSize(true);
        recyclerViewZReading.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        zReadingAdapter = new ZReadingAdapter(getActivity());
        recyclerViewZReading.setAdapter(zReadingAdapter);
    }

    private void initViewModel(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        cutOffViewModel = POSApplication.getInstance().getCutOffViewModel();
        endOfDayViewModel = POSApplication.getInstance().getEndOfDayViewModel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        transactionsViewModel.fetchCompleteTransactions().removeObservers(this);
        transactionsViewModel.fetchCompleteTransactionsCutOff().removeObservers(this);
        cutOffViewModel.fetchCutOffForTodayToReprint().removeObservers(this);
        endOfDayViewModel.fetchEndOfDayForTodayToReprint().removeObservers(this);
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

}
