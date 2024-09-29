package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.ServerUserAuthentication;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.entity.AuthenticatedMachineUser;
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.Roles;
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.payload.LoginPayload;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.response.entity.AuthenticateServerResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.viewmodel.AuthenticatedMachineUserViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public abstract class AuthenticateDialog extends Dialog {

    private boolean authenticateLocal = true;
    private int permissionId = 0;
    private EditText txtAuthDialogUsername, txtAuthDialogPassword;
    private MaterialButton btnAuthNegative, btnAuthPositive;
    private UsersViewModel usersViewModel;
    private AuthenticatedMachineUserViewModel authenticatedMachineUserViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private Activity activity;
    private Gson gson;
    private Cache cache;

    interface RequestAPI {
        @POST("/api/login")
        Call<AuthenticateServerResponse> Login(@Body LoginPayload loginPayload);
    }

    public abstract void authentication(boolean success, @Nullable String message,@Nullable UserAuthentication authorizeUser);

    public AuthenticateDialog(Activity activity){
        super(activity);
        this.activity = activity;
        this.gson = new Gson();
        this.cache = new Cache(activity);
    }

    //Set For Online Authenticate
    public void setOnlineAuthenticateViewModels(AuthenticatedMachineUserViewModel authenticatedMachineUserViewModel){
        this.authenticatedMachineUserViewModel = authenticatedMachineUserViewModel;
    }

    //Set For Local Authenticate
    public void setPermissionId(int permissionId){
        this.permissionId = permissionId;
    }

    public void setOfflineAuthenticateViewModels(UsersViewModel usersViewModel, RolesViewModel rolesViewModel, PermissionsViewModel permissionsViewModel){
        this.usersViewModel = usersViewModel;
        this.rolesViewModel = rolesViewModel;
        this.permissionsViewModel = permissionsViewModel;
    }

    public void setAuthenticateLocal(boolean authenticateLocal) {
        this.authenticateLocal = authenticateLocal;
    }

    private void initialize(){
        txtAuthDialogUsername = findViewById(R.id.txtAuthDialogUsername);
        txtAuthDialogPassword = findViewById(R.id.txtAuthDialogPassword);
        btnAuthNegative = findViewById(R.id.btnAuthNegative);
        btnAuthPositive = findViewById(R.id.btnAuthPositive);
    }

    private void checkEnvironment(){
        if(BuildConfig.ENV.equals("PRODUCTION")){
            txtAuthDialogUsername.setText("");
            txtAuthDialogPassword.setText("");
        }
    }

    private void checkForLocalAuth(){
        if(authenticateLocal && BuildConfig.ENV.equals("DEVELOPMENT")){
            txtAuthDialogUsername.setText("");
            txtAuthDialogPassword.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_authenticate);
        setCancelable(false);
        initialize();
        checkEnvironment();
        checkForLocalAuth();
        btnAuthNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnAuthPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });
    }

    public void authenticate(){
        if(txtAuthDialogUsername.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please input a username.", Toast.LENGTH_LONG).show();
            txtAuthDialogUsername.requestFocus();
        } else if (txtAuthDialogPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please input a password.", Toast.LENGTH_LONG).show();
            txtAuthDialogPassword.requestFocus();
        }
        else{
            if(authenticateLocal){
                authLocal();
            }
            else{
                authServer();
            }
        }
    }

    private void authLocal(){
        try {
            hide();
            LoadingDialog.getInstance().startLoadingDialog(activity, "Authenticating...");
            Users users = usersViewModel.findByUsernameOrEmail(txtAuthDialogUsername.getText().toString());
            if(users != null){
                if(BCrypt.verifyer().verify(txtAuthDialogPassword.getText().toString().toCharArray(), users.getPassword()).verified){
                    List<Roles> roles = rolesViewModel.fetchByUserId(users.getCoreId());
                    if(roles.size() != 0){
                        Permissions permissions = permissionsViewModel.fetchByRoleUserIdPermissionId(roles.get(0).getCoreId(), users.getCoreId(), permissionId);
                        if(permissions != null){
                            dismiss();
                            LoadingDialog.getInstance().closeLoadingDialog();
                            authentication(true, "There is a permission for this user.", new UserAuthentication(
                                    users.getCoreId(),
                                    users.getName(),
                                    users.getUsername(),
                                   "",
                                    null
                            ));
                        }
                        else{
                            clearAuthForm();
                            LoadingDialog.getInstance().closeLoadingDialog();
                            show();
                            authentication(false, "There is no permission for this user.", null);
                        }
                    }
                    else {
                        clearAuthForm();
                        LoadingDialog.getInstance().closeLoadingDialog();
                        show();
                        authentication(false, "There is no role for this user.", null);
                    }
                }
                else{
                    clearAuthForm();
                    LoadingDialog.getInstance().closeLoadingDialog();
                    show();
                    authentication(false, "Invalid username or password.", null);
                }
            }
            else{
                clearAuthForm();
                LoadingDialog.getInstance().closeLoadingDialog();
                show();
                authentication(false, "Invalid username or password.", null);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void authServer(){
        hide();
        LoadingDialog.getInstance().startLoadingDialog(activity, "Authenticating to server...");
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.Login(new LoginPayload(txtAuthDialogUsername.getText().toString(), txtAuthDialogPassword.getText().toString())).enqueue(new Callback<AuthenticateServerResponse>() {
            @Override
            public void onResponse(Call<AuthenticateServerResponse> call, Response<AuthenticateServerResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    authenticatedMachineUserViewModel.insert(new AuthenticatedMachineUser(txtAuthDialogUsername.getText().toString(), EncryptDecrypt.getInstance().encrypt(response.body().getData().getToken()), response.body().getData().getName()));
                                } catch (ExecutionException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            AuthenticatedMachineUser authenticatedMachineUser = authenticatedMachineUserViewModel.fetch();
                                            POSApplication.getInstance().setServerUserAuthentication(new ServerUserAuthentication(authenticatedMachineUser.getId(), authenticatedMachineUser.getName(), authenticatedMachineUser.getUsername(), authenticatedMachineUser.getToken()));
                                            cache.saveString("serverUserAuthentication", gson.toJson(POSApplication.getInstance().getServerUserAuthentication()));
                                            dismiss();
                                            LoadingDialog.getInstance().closeLoadingDialog();
                                            authentication(true, null, null);
                                        } catch (ExecutionException | InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });

                            }
                        });
                    }
                    else{
                        clearAuthForm();
                        LoadingDialog.getInstance().closeLoadingDialog();
                        show();
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        authentication(false, response.body().getMessage(), null);
                    }
                }
                else{
                    clearAuthForm();
                    LoadingDialog.getInstance().closeLoadingDialog();
                    show();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    authentication(false, errorBody.getMessage(), null);
                }
            }

            @Override
            public void onFailure(Call<AuthenticateServerResponse> call, Throwable t) {
                Log.d("authServer", "onFailure: " + t.getMessage());
                LoadingDialog.getInstance().closeLoadingDialog();
                show();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearAuthForm(){
        txtAuthDialogUsername.setText("");
        txtAuthDialogPassword.setText("");
        txtAuthDialogUsername.requestFocus();
    }

}
