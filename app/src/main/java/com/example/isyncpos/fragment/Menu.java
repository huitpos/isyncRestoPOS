package com.example.isyncpos.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.MenuItemsAdapter;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.Font;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.dialog.MenuApplyQuantityDialog;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.TestConnectionResponse;
import com.example.isyncpos.viewmodel.BranchViewModel;
import com.example.isyncpos.viewmodel.CompanyViewModel;
import com.example.isyncpos.viewmodel.DepartmentsViewModel;
import com.example.isyncpos.viewmodel.ProductsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public class Menu extends Fragment implements View.OnClickListener {

    private ImageView imageOnlineIndicator;
    private TextView labelUser, txtOnlineIndicator, txtCompanyBranch;
    private TextClock labelClock;
    private EditText textSearchMenu;
    private LinearLayout linearLayout;
    private MenuItemsAdapter menuItemsAdapter;
    private RecyclerView menuRecyclerView;
    private DepartmentsViewModel departmentsViewModel;
    private ProductsViewModel productsViewModel;
    private BranchViewModel branchViewModel;
    private CompanyViewModel companyViewModel;
    private Dates date;
    private Timer timer, testConnectionTimer;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private Gson gson;
    private boolean firstBoot = true;
    private boolean canRequestTestConnection = true;
    private MaterialButton btnMenuQuantity;
    private MaterialButton previousDepartmentButtonSelected;
    private Order order;

    interface RequestAPI {
        @Headers({
            "accept: application/json"
        })
        @GET("/api/test-connection")
        Call<TestConnectionResponse> TestConnection(@Header("Authorization") String authorization);
    }

    public static Menu newMenu(){
        return new Menu();
    }

    public void setArgs(Order order){
        this.order = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //Initialize
        initialize();
        //This will set the authenticated user
        labelUser.setText("Welcome, " + POSApplication.getInstance().getUserAuthentication().getName());
        //This will set the datetime clock base on the timezone your in
        labelClock.setTimeZone("Asia/Manila");
        labelClock.setText(date.now());

        //Set company and branch name
        branchViewModel.fetchBranchInformation().observe(getActivity(), new Observer<Branch>() {
            @Override
            public void onChanged(Branch branch) {
                txtCompanyBranch.setText("Machine No.: " + POSApplication.getInstance().getMachineDetails().getMachineNumber() + " | " + POSApplication.getInstance().getCompany().getTradeName() + " - " + branch.getName());
            }
        });

        companyViewModel.fetchCompanyInformation().observe(getActivity(), new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                Log.d("onChanged", "onChanged: company");
                txtCompanyBranch.setText("Machine No.: " + POSApplication.getInstance().getMachineDetails().getMachineNumber() + " | " + company.getTradeName() + " - " + POSApplication.getInstance().getBranch().getName());
            }
        });

        productsViewModel.fetchMenuProducts().observe(getActivity(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                menuRecyclerView.setItemViewCacheSize(products.size());
                menuItemsAdapter.submitList(products);
            }
        });

        textSearchMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timer != null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                    List<Products> searchProducts = searchProduct(textSearchMenu.getText().toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productsViewModel.setMenuProducts(searchProducts);
                        }
                    });
                    }
                }, 500);
            }
        });

        layoutParams.setMargins(5, 0, 10, 0);
        departmentsViewModel.fetchAll().observe(getActivity(), new Observer<List<Departments>>() {
            @Override
            public void onChanged(List<Departments> departments) {
                linearLayout.removeAllViews();
                POSApplication.getInstance().setSortDepartmentId(departments.get(0).getCoreId());
                //This will set all the products to the recycler view base on the first department
                for(Departments item : departments){
                    MaterialButton materialButton = new MaterialButton(getContext());
                    materialButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(getView().getResources()));
                    materialButton.setText(item.getName());
                    materialButton.setTextSize(12);
                    materialButton.setId(item.getCoreId());
                    materialButton.setCornerRadius(60);
                    materialButton.setTextColor(getView().getResources().getColor(R.color.white, null));
//                    materialButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.baseline_lock_24));
                    materialButton.setIconTintResource(R.color.white);
//        materialButtonAll.setBackgroundColor(getResources().getColor(R.color.lightblue, null));
                    materialButton.setLayoutParams(layoutParams);
                    materialButton.setOnClickListener(Menu.this);
                    if(firstBoot){
                        materialButton.performClick();
                        firstBoot = false;
                    }
                    linearLayout.addView(materialButton);
                }
            }
        });

        btnMenuQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuApplyQuantityDialog menuApplyQuantityDialog = MenuApplyQuantityDialog.newMenuApplyQuantityDialog();
                menuApplyQuantityDialog.setCancelable(false);
                menuApplyQuantityDialog.setArgs(Menu.this);
                menuApplyQuantityDialog.show(getActivity().getSupportFragmentManager(), "Menu Apply Quantity");
            }
        });

        testConnection();

    }

    private void initialize(){
        initViewModels();
        initRecyclerView();
        gson = new Gson();
        date = new Dates();
        labelUser = getView().findViewById(R.id.labelUser);
        labelClock = getView().findViewById(R.id.labelClock);
        linearLayout = getView().findViewById(R.id.layoutDepartment);
        textSearchMenu = getView().findViewById(R.id.textSearchMenu);
        imageOnlineIndicator = getView().findViewById(R.id.imageOnlineIndicator);
        txtOnlineIndicator = getView().findViewById(R.id.txtOnlineIndicator);
        txtCompanyBranch = getView().findViewById(R.id.txtCompanyBranch);
        btnMenuQuantity = getView().findViewById(R.id.btnMenuQuantity);
    }

    private void initViewModels(){
        departmentsViewModel = POSApplication.getInstance().getDepartmentsViewModel();
        productsViewModel = POSApplication.getInstance().getProductsViewModel();
        branchViewModel = POSApplication.getInstance().getBranchViewModel();
        companyViewModel = POSApplication.getInstance().getCompanyViewModel();
    }

    private void initRecyclerView(){
        try {
            menuRecyclerView = getView().findViewById(R.id.recyclerViewItems);
            menuRecyclerView.setHasFixedSize(true);
            menuRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
            menuItemsAdapter = new MenuItemsAdapter(getActivity().getSupportFragmentManager(), getContext());
            productsViewModel.setMenuProducts(productsViewModel.fetchAllByDepartment(POSApplication.getInstance().getSortDepartmentId()));
            menuRecyclerView.setAdapter(menuItemsAdapter);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        branchViewModel.fetchBranchInformation().removeObservers(this);
        companyViewModel.fetchCompanyInformation().removeObservers(this);
        productsViewModel.fetchMenuProducts().removeObservers(this);
        departmentsViewModel.fetchAll().removeObservers(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                if(POSApplication.getInstance().getSortDepartmentId() != 0){
                    unHighLightPreviousDepartment();
                }
                previousDepartmentButtonSelected = view.findViewById(view.getId());
                highLightSelectedDepartment(view.findViewById(view.getId()));
                POSApplication.getInstance().setSortDepartmentId(view.getId());
                sortProductDepartment();
                break;
        }
    }

    private void highLightSelectedDepartment(MaterialButton button){
        button.setBackgroundColor(getResources().getColor(R.color.acolor, null));
    }

    private void unHighLightPreviousDepartment(){
        if(previousDepartmentButtonSelected != null){
            previousDepartmentButtonSelected.setBackgroundColor(Color.rgb(103, 80, 164));
        }
    }

    public void sortProductDepartment(){
        try {
            productsViewModel.setMenuProducts(productsViewModel.fetchAllByDepartment(POSApplication.getInstance().getSortDepartmentId()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Products> searchProduct(String search){
        try {
            if (search == null || search.isEmpty()){
                return productsViewModel.fetchAllByDepartment(POSApplication.getInstance().getSortDepartmentId());
            }
            else{
                return productsViewModel.search(search);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testConnection(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        if(canRequestTestConnection){
            canRequestTestConnection = false;
            requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
                @Override
                public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            canRequestTestConnection = true;
                            imageOnlineIndicator.setColorFilter(Color.rgb(0,255,0));
                            txtOnlineIndicator.setText("ONLINE");
                            order.enablePaymentButtonForTakeOrder();
                        }
                        else{
                            canRequestTestConnection = true;
                            imageOnlineIndicator.setColorFilter(Color.rgb(255, 0, 0));
                            txtOnlineIndicator.setText("OFFLINE");
                            order.disablePaymentButtonForTakeOrder();
                        }
                    }
                    else{
                        canRequestTestConnection = true;
                        imageOnlineIndicator.setColorFilter(Color.rgb(255, 0, 0));
                        txtOnlineIndicator.setText("OFFLINE");
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        order.disablePaymentButtonForTakeOrder();
                    }
                }

                @Override
                public void onFailure(Call<TestConnectionResponse> call, Throwable t) {
                    canRequestTestConnection = true;
                    imageOnlineIndicator.setColorFilter(Color.rgb(255, 0, 0));
                    txtOnlineIndicator.setText("OFFLINE");
                    Log.d("testConnection", "onFailure: " + t.getMessage());
                    order.disablePaymentButtonForTakeOrder();
                }
            });
        }
    }

    public void applyMenuQuantity(String quantity){
        btnMenuQuantity.setText("Qty: " + quantity);
    }

    public void clearMenuSearchText(){
        textSearchMenu.getText().clear();
    }

    private String getDeviceSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("height", String.valueOf(height));
        Log.d("width", String.valueOf(width));
        if(height >= BuildConfig.APP_DEFAULT_SMALL_DEVICE_MIN && height <= BuildConfig.APP_DEFAULT_SMALL_DEVICE_MAX){
            Log.d("getDeviceSize", "small");
            return "small";
        }
        else{
            Log.d("getDeviceSize", "large");
            return "large";
        }
    }

}
