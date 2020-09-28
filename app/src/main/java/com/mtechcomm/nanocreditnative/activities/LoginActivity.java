package com.mtechcomm.nanocreditnative.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.models.LoginUserDetailsModel;
import com.mtechcomm.nanocreditnative.models.LoginUserResponseModel;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.net.Mtech_API_Interface;
import com.mtechcomm.nanocreditnative.net.Mtech_API_client;
import com.mtechcomm.nanocreditnative.viewmodels.AccessTokenViewModel;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    Button singin_button, register_button;
    private AccessTokenViewModel accessTokenViewModel;
    private EditText username_EDT, password_EDT;

    ProgressBar progressBar;
    Button forgot_pin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register_button = (Button) findViewById(R.id.register_btn);
        singin_button = (Button)findViewById(R.id.sign_in_btn);
        forgot_pin_btn = findViewById(R.id.forgot_pin_btn);

        username_EDT = (EditText)findViewById(R.id.login_user_username);
        username_EDT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password_EDT = (EditText)findViewById(R.id.login_user_password);
        password_EDT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        accessTokenViewModel = ViewModelProviders.of(this).get(AccessTokenViewModel.class);
        accessTokenViewModel.init();


        singin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new GetRequestToken(getApplicationContext()).execute();
//                accessTokenViewModel.getApiAccessToken();


//                getToken();
//                progressBar.setVisibility(View.VISIBLE);
//                progressBar.setIndeterminate(true);
//                progressBar.animate();

                String username = username_EDT.getText().toString();
                String password = password_EDT.getText().toString();

               performLogin(username, password);

//                AppUser appUser = retrieveUser();
//                if (appUser == null){
//                    //Toast.makeText(LoginActivity.this, R.string.no_user_found_on_this_device,Toast.LENGTH_SHORT).show();
//
//                    DesignerToast.Error(LoginActivity.this, String.valueOf(R.string.no_user_found_on_this_device), Gravity.CENTER,Toast.LENGTH_SHORT);
//
//                    progressBar.setVisibility(View.GONE);
//                    progressBar.setIndeterminate(false);
//                    progressBar.clearAnimation();
//
//                }else {
//                    if ((username.equals(appUser.getEmailAddress())) && (password.equals(appUser.getPassword()))){
//
//                        progressBar.setVisibility(View.GONE);
//                        progressBar.setIndeterminate(false);
//                        progressBar.clearAnimation();
//
//                        Toast.makeText(LoginActivity.this,appUser.getSurname() + ", you are welcome.",Toast.LENGTH_SHORT).show();
//                       // Log.d(TAG, "onClick: Existing AppID : " + appUser.getLoanApplicationID());
//                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                        startActivity(intent);
//
//                        if (appUser.isPhoneVerified()){
//                            Toast.makeText(LoginActivity.this,appUser.getSurname() + ", you are welcome",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                            startActivity(intent);
//                        }else {
//                            Toast.makeText(LoginActivity.this,appUser.getSurname() + ", you need to verify your phone number",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
//                            intent.putExtra("mobile", appUser.getPhones().get(0));
//                            startActivity(intent);
//                        }
//
//
//                    }else{
//                        progressBar.setVisibility(View.GONE);
//                        progressBar.setIndeterminate(false);
//                        progressBar.clearAnimation();
//
//                        new SweetAlertDialog(LoginActivity.this)
//                                .setTitleText("Login")
//                                .setContentText("Incorrect Username and Password combination !!!")
//                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                        sweetAlertDialog.dismissWithAnimation();
//                                    }
//                                })
//                                .show();
//                    }
//                }


            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgot_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DesignerToast.Info(LoginActivity.this,"this is still being implemented",Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        });

    }

    private void performLogin(String username, String password) {
        Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
        Call<LoginUserResponseModel> call = mtech_api_interface.loginUser(new LoginUserDetailsModel(username,password));

        call.enqueue(new CustomCallBack<>(LoginActivity.this, new Callback<LoginUserResponseModel>() {
            @Override
            public void onResponse(Call<LoginUserResponseModel> call, Response<LoginUserResponseModel> response) {

                Log.d(TAG, "onResponse: Resp Code : " + response.code());
                if (response.code() == 201) {
                    Log.d(TAG, "onResponse: " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.body());

                    LoginUserResponseModel model = response.body();
                    Log.d(TAG, "onResponse: " + model.getEmail());

//                            AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, boolean phoneVerified)
                    String phone = model.getPhonenumber();
                    ArrayList<String> phones = new ArrayList<>();
                    phones.add(phone);
                    boolean verified = false;
                    if (model.getNumberVerified().equals("true") || model.getNumberVerified().equals("t"))
                        verified = true;

                    int customerID = 0;
                    if (model.getCid() != null)
                        customerID = Integer.valueOf(model.getCid());

                    String documentID = "";
                    if (model.getDocid() != null)
                        documentID = model.getDocid();

                    int loanID = 0;
                    if (model.getLoanid() != null)
                        loanID = Integer.valueOf(model.getLoanid());

                    String loanStatus = "";
                    if (model.getLstatus() != null)
                        loanStatus = model.getLstatus();

                    String last_login = model.getLast_login();

                    // "cid": null,
//             "docid": null,
//             "loanid": "5050",
//             "lstatus": "t",
//             "last_login_datetime": "2020-09-04 18:48:45.642855"

                    AppUser appUser = new AppUser(model.getFirstname(),model.getLastname(),phones,model.getEmail(),
                            model.getDob(),model.getPassword(),model.getAccountnumber(),verified,
                            customerID, documentID, loanID, loanStatus,last_login); //retrieveUser();

                    saveUserInfo(appUser);

//                    if (verified){

                        Toast.makeText(LoginActivity.this,appUser.getSurname() + ", you are welcome",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
//                    }else {
//                        Toast.makeText(LoginActivity.this,appUser.getSurname() + ", you need to verify your phone number",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
//                        intent.putExtra("mobile", appUser.getPhones().get(0));
//                        intent.putExtra("source", "login");
//                        startActivity(intent);
//                    }

                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    DesignerToast.Error(LoginActivity.this, errorResponse.getError(),Gravity.CENTER,Toast.LENGTH_SHORT);

                }


            }

            @Override
            public void onFailure(Call<LoginUserResponseModel> call, Throwable t) {
                DesignerToast.Error(LoginActivity.this, t.getMessage(), Gravity.CENTER,Toast.LENGTH_SHORT);
                Log.d(TAG, "onResponse: " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        }));

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void saveUserInfo(AppUser appUser) {
        SharedPreferences preferences = getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appUser);
        prefsEditor.putString("app_user", json);
        prefsEditor.commit();
    }

    private AppUser retrieveUser(){

        SharedPreferences preferences = getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")){
//            Toast.makeText(LoginActivity.this,"Null",Toast.LENGTH_SHORT).show();
            return  null;
        }
        AppUser appUser = gson.fromJson(json, AppUser.class);

       // Toast.makeText(LoginActivity.this,appUser.getSurname(),Toast.LENGTH_SHORT).show();

        return appUser;
    }

}
