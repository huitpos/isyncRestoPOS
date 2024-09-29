package com.example.isyncpos.adapters;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.ObjectsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.dialog.ItemMenuInformationDialog;
import com.example.isyncpos.entity.Products;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuItemsAdapter extends ListAdapter<Products, MenuItemsAdapter.ViewHolder> {

    private MenuItemListener menuItemListener;
    private Generate generate;
    private FragmentManager fragmentManager;
    private Context context;

    public interface MenuItemListener{
        void onItemMenuClick(Products products, double qty);
    }

    public MenuItemsAdapter(FragmentManager fragmentManager, Context context) {
        super(DIFF_CALLBACK);
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Products> DIFF_CALLBACK = new DiffUtil.ItemCallback<Products>() {
        @Override
        public boolean areItemsTheSame(Products oldItem, Products newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Products oldItem, Products newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        generate = new Generate();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Products productList = getItem(position);
        Picasso.get().load(new File(Environment.getExternalStorageDirectory(), "Documents/images/" + productList.getImage())).resize(150, 150).placeholder(R.drawable.isyncdefaultproductimage).into(holder.productImage);
        holder.productName.setText(productList.getName());
        holder.productDescription.setText(productList.getDescription());
        holder.productPrice.setText(generate.toTwoDecimalWithComma(productList.getPrice()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, productDescription, productPrice;
        private ImageView productImage;
        private MaterialButton btnItemInformation;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.menuItemImage);
            productName = itemView.findViewById(R.id.menuItemName);
            productDescription = itemView.findViewById(R.id.menuItemDescription);
            productPrice = itemView.findViewById(R.id.menuItemPrice);
            btnItemInformation = itemView.findViewById(R.id.btnItemInformation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuItemListener.onItemMenuClick(getItem(getAbsoluteAdapterPosition()), POSApplication.getInstance().getTotalQuantity());
                }
            });

            btnItemInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemMenuInformationDialog itemMenuInformationDialog = ItemMenuInformationDialog.newItemMenuInformationDialog();
                    itemMenuInformationDialog.setCancelable(false);
                    itemMenuInformationDialog.setArgs(getItem(getAbsoluteAdapterPosition()), context);
                    itemMenuInformationDialog.show(fragmentManager, "Menu Item Information Dialog");
                }
            });

            //Set
            productDescription.setVisibility(View.GONE);


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof MenuItemListener){
            menuItemListener = (MenuItemListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement MenuItemListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        menuItemListener = null;
    }

}
