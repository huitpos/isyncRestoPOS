package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class OrderItemsAdapter extends ListAdapter<Orders, OrderItemsAdapter.ViewHolder> {

    private OrderItemListener orderItemListener;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private ArrayList<Orders> selectedListOrders = new ArrayList<>();
    private Generate generate;
    private Activity activity;

    public interface OrderItemListener{
        void onOrderItemUpdateQuantity(int orderId,@Nullable UserAuthentication authorizeUser);
        void onOrderItemUpdatePrice(int orderId,@Nullable UserAuthentication authorizeUser);
        void onOrderItemReturnItem(int orderId);
        void onOrderItemUnReturnItem(int orderId);
    }

    public OrderItemsAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
        initialize();
    }

    private void initialize(){
        posProcess = POSApplication.getInstance().getPosProcess();
        initViewModels();
    }

    private void initViewModels(){
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    private static final DiffUtil.ItemCallback<Orders> DIFF_CALLBACK = new DiffUtil.ItemCallback<Orders>() {
        @Override
        public boolean areItemsTheSame(Orders oldItem, Orders newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Orders oldItem, Orders newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_order, parent, false);
        generate = new Generate();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Orders orderItemsList = getItem(position);
        String serialNumber = "";
        if(orderItemsList.getIsReturn() == 1){
            if(orderItemsList.getSerialNumber() != null){
                if(!orderItemsList.getSerialNumber().equals("")) serialNumber = " S/N: " + orderItemsList.getSerialNumber();
            }
            holder.productDescription.setText("®" + orderItemsList.getName() + serialNumber);
            holder.productDescription.setTextColor(Color.parseColor("#FF0000"));
            holder.productQty.setText(String.valueOf(orderItemsList.getQty()));
            holder.productQty.setTextColor(Color.parseColor("#FF0000"));
            holder.productPrice.setText(generate.toTwoDecimalWithComma(orderItemsList.getAmount()));
            holder.productPrice.setTextColor(Color.parseColor("#FF0000"));
            holder.productTotal.setText(generate.toTwoDecimalWithComma(orderItemsList.getTotal()));
            holder.productTotal.setTextColor(Color.parseColor("#FF0000"));
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white, null));
        }
        else{
            if(orderItemsList.getSerialNumber() != null){
                if(!orderItemsList.getSerialNumber().equals("")) serialNumber = " S/N: " + orderItemsList.getSerialNumber();
            }
            holder.productDescription.setText(orderItemsList.getName() + serialNumber);
            holder.productDescription.setTextColor(Color.parseColor("#544f59"));
            holder.productQty.setText(String.valueOf(orderItemsList.getQty()));
            holder.productQty.setTextColor(Color.parseColor("#544f59"));
            holder.productPrice.setText(generate.toTwoDecimalWithComma(orderItemsList.getAmount()));
            holder.productPrice.setTextColor(Color.parseColor("#544f59"));
            holder.productTotal.setText(generate.toTwoDecimalWithComma(orderItemsList.getTotal()));
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white, null));
            if(orderItemsList.getDiscountDetailsId() != 0){
                holder.productTotal.setTextColor(holder.itemView.getResources().getColor(R.color.scolor, null));
            }
            else {
                holder.productTotal.setTextColor(Color.parseColor("#544f59"));
            }
        }
    }

    public Integer getUpdatedItemPosition(){
        int position = 0;
        if(POSApplication.getInstance().getLastUpdateProductId() != 0){
            for(Orders item : getCurrentList()){
                if(item.getProductId() == POSApplication.getInstance().getLastUpdateProductId()){
                    return position;
                }
                position += 1;
            }
        }
        return position;
    }

    public ArrayList<Orders> getSelectedListOrders(){
        return selectedListOrders;
    }

    public void clearSelectedListOrders(){
        selectedListOrders.clear();
    }

    public void selectAllListOrders(){
        for(Orders order : getCurrentList()){
            Log.d("ORDERS", "selectAllListOrders: " + order.getName());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productDescription, productQty, productPrice, productTotal;
        private boolean isSelected = false;

        public ViewHolder(View itemView) {
            super(itemView);
            productDescription = itemView.findViewById(R.id.txtOrderItemDescription);
            productQty = itemView.findViewById(R.id.txtOrderItemQty);
            productPrice = itemView.findViewById(R.id.txtOrderItemPrice);
            productTotal = itemView.findViewById(R.id.txtOrderItemTotal);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopupMenu(view);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isSelected){
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.white, null));
                        isSelected = false;
                        selectedListOrders.remove(getItem(getAbsoluteAdapterPosition()));
                    }
                    else{
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.yellow, null));
                        isSelected = true;
                        selectedListOrders.add(getItem(getAbsoluteAdapterPosition()));
                    }
                }
            });

        }

        private void showPopupMenu(View view){
            Context context = new ContextThemeWrapper(view.getContext(), R.style.popupMenuStyle);
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.inflate(R.menu.item_order_popup);
            //Get The Menu Items
            Menu menu = popupMenu.getMenu();
            //Get The Selected Item On Order Fragment
            Orders orders = getItem(getAbsoluteAdapterPosition());
            //This will check if the return item is return
            if(orders.getIsReturn() != 0) {
                menu.getItem(2).setTitle("Cancel Return Item");
            }
            else{
                menu.getItem(2).setTitle("Return Item");
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == R.id.popupItemMenuUpdateQty){
                        if(orders.getWithSerial() != 1){
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_ITEM_SELECT_UPDATE_QTY)){
                                orderItemListener.onOrderItemUpdateQuantity(orders.getId(), null);
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            orderItemListener.onOrderItemUpdateQuantity(orders.getId(), authorizeUser);
                                        }
                                        else{
                                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_ITEM_SELECT_UPDATE_QTY);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                        else{
                            Toast.makeText(context, "This is a item with serial cannot update the quantity.", Toast.LENGTH_LONG).show();
                        }
                    } else if (item.getItemId() == R.id.popupItemMenuUpdatePrice) {
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_ITEM_SELECT_UPDATE_PRICE)){
                            orderItemListener.onOrderItemUpdatePrice(orders.getId(), null);
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        orderItemListener.onOrderItemUpdatePrice(orders.getId(), authorizeUser);
                                    }
                                    else{
                                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_ITEM_SELECT_UPDATE_PRICE);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                    else if(item.getItemId() == R.id.popupItemMenuReturnItem) {
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_ITEM_SELECT_RETURN_ITEM)){
                            //This will check if the return item is return
                            if(orders.getIsReturn() != 0){
                                orderItemListener.onOrderItemUnReturnItem(orders.getId());
                            }
                            else{
                                orderItemListener.onOrderItemReturnItem(orders.getId());
                            }
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        //This will check if the return item is return
                                        if(orders.getIsReturn() != 0){
                                            orderItemListener.onOrderItemUnReturnItem(orders.getId());
                                        }
                                        else{
                                            orderItemListener.onOrderItemReturnItem(orders.getId());
                                        }
                                    }
                                    else{
                                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_ITEM_SELECT_RETURN_ITEM);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                    return true;
                }
            });
            popupMenu.show();
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof OrderItemsAdapter.OrderItemListener){
            orderItemListener = (OrderItemsAdapter.OrderItemListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement OrderItemListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        orderItemListener = null;
    }

}
