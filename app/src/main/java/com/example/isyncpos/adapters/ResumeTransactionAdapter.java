package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.CustomerNameDialog;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;

public class ResumeTransactionAdapter extends ListAdapter<Transactions, ResumeTransactionAdapter.ViewHolder> {

    private ResumeTransactionListener resumeTransactionListener;
    private AlertDialog alertDialog;
    private TransactionsViewModel transactionsViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private Activity activity;

    public interface ResumeTransactionListener{
        void resumeTransaction(int transactionId);
        void backoutTransaction(int transactionId);
    }

    public ResumeTransactionAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Transactions> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transactions>() {
        @Override
        public boolean areItemsTheSame(Transactions oldItem, Transactions newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Transactions oldItem, Transactions newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_resume_transaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        posProcess = POSApplication.getInstance().getPosProcess();
        initViewModels();
        return viewHolder;
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transactions transactionList = getItem(position);
        if(transactionList.getId() != POSApplication.getInstance().getCurrentTransaction()){
            String info = "";
            if(transactionList.getCustomerName() == null){
                info = "#: " + transactionList.getControlNumber();
            }
            else{
                info = transactionList.getCustomerName() + " " + "#: " + transactionList.getControlNumber();
            }
            holder.labelResumeTransaction.setText(info);
        }
        else{
            holder.resumeTransactionViewHolder.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView labelResumeTransaction;
        private MaterialButton btnResumeTransaction, btnResumeTransactionBackout, btnUpdateCustomerName;
        private LinearLayout resumeTransactionViewHolder;

        public ViewHolder(View itemView){
            super(itemView);
            labelResumeTransaction = itemView.findViewById(R.id.labelResumeTransaction);
            btnResumeTransaction = itemView.findViewById(R.id.btnResumeTransaction);
            btnResumeTransactionBackout = itemView.findViewById(R.id.btnResumeTransactionBackout);
            resumeTransactionViewHolder = itemView.findViewById(R.id.resumeTransactionViewHolder);
            btnUpdateCustomerName = itemView.findViewById(R.id.btnUpdateCustomerName);

            btnResumeTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_RESUME_TRANSACTION_RESUME)){
                        processResumeTransaction(getItem(getAbsoluteAdapterPosition()).getId());
                        alertDialog.dismiss();
                    }
                    else{
                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                            @Override
                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                if(success){
                                    processResumeTransaction(getItem(getAbsoluteAdapterPosition()).getId());
                                    alertDialog.dismiss();
                                }
                                else{
                                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_RESUME_TRANSACTION_RESUME);
                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                        authenticateDialog.setCancelable(false);
                        authenticateDialog.show();
                    }
                }
            });

            btnResumeTransactionBackout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.hide();
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to backout this transaction #: " + getItem(getAbsoluteAdapterPosition()).getControlNumber());
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_BACKOUT)){
                                processBackoutTransaction(getItem(getAbsoluteAdapterPosition()).getId());
                                alertDialog.show();
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            processBackoutTransaction(getItem(getAbsoluteAdapterPosition()).getId());
                                            alertDialog.show();
                                        }
                                        else{
                                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_BACKOUT);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            btnUpdateCustomerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Transactions transactions =  getItem(getAbsoluteAdapterPosition());
                    CustomerNameDialog customerNameDialog = new CustomerNameDialog(activity, transactions) {
                        @Override
                        public void process(boolean success, Transactions transactions) {
                            if(success){
                                transactionsViewModel.updateTransactionCustomerName(transactions.getId(), transactions.getCustomerName());
                            }
                        }
                    };
                    customerNameDialog.setCancelable(false);
                    customerNameDialog.show();
                }
            });

        }

    }

    private void processResumeTransaction(int transactionId){
        resumeTransactionListener.resumeTransaction(transactionId);
    }

    private void processBackoutTransaction(int transactionId){
        resumeTransactionListener.backoutTransaction(transactionId);
    }

    public void setAlertDialog(AlertDialog alertDialog){
        this.alertDialog = alertDialog;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof ResumeTransactionAdapter.ResumeTransactionListener){
            resumeTransactionListener = (ResumeTransactionAdapter.ResumeTransactionListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement ResumeTransactionListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        resumeTransactionListener = null;
    }

}
