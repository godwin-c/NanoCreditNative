package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.client.android.CaptureActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CreateUserDetails;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.classes.NetworkAvaillabilityClass;
import com.mtechcomm.nanocreditnative.classes.NetworkProviderClass;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.CustomerEnquiryResult;
import com.mtechcomm.nanocreditnative.models.CustomerModel;
import com.mtechcomm.nanocreditnative.models.CustomerModelResult;
import com.mtechcomm.nanocreditnative.models.EnquiryModel;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class CreateUserFragment extends Fragment {

    private static final String TAG = CreateUserFragment.class.getSimpleName();
    private EditText create_user_apply_loan_account_number, create_user_apply_loan_phone_number, create_user_apply_loan_id_card_number_txt;
    private Button create_user_apply_loan_btn_scan_id, create_user_apply_loan_submit;
    View view;

    int PERMISSION_ALL = 11110;
    String[] PERMISSIONS;

    AppUser appUser;
    CustomerModel customerModel;
    AuthenticationResponse authenticationResponse;

    public CreateUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_user, container, false);

        create_user_apply_loan_account_number = view.findViewById(R.id.create_user_apply_loan_account_number);
        create_user_apply_loan_phone_number = view.findViewById(R.id.create_user_apply_loan_phone_number);
        create_user_apply_loan_id_card_number_txt = view.findViewById(R.id.create_user_apply_loan_id_card_number_txt);

        create_user_apply_loan_btn_scan_id = view.findViewById(R.id.create_user_apply_loan_btn_scan_id);
        create_user_apply_loan_submit = view.findViewById(R.id.create_user_apply_loan_submit);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appUser = retrieveUser();

        ArrayList<String> phones = appUser.getPhones();
        create_user_apply_loan_phone_number.setText(phones.get(0));

        if (appUser.getAccountNumber() != null)
            create_user_apply_loan_account_number.setText(appUser.getAccountNumber());

        if (appUser.getDocumentID() != null)
            create_user_apply_loan_id_card_number_txt.setText(appUser.getDocumentID());

//        create_user_apply_loan_phone_number.setInputType(InputType.TYPE_NULL);

        create_user_apply_loan_btn_scan_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

        create_user_apply_loan_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
//                callMainFragment();
            }
        });
    }

    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("show_created_user");
        }

    }

    private void checkValidation() {
        if (!validateField(create_user_apply_loan_account_number, getContext().getString(R.string.account_number_missing))) {
            return;
        }
        if (create_user_apply_loan_id_card_number_txt.getText().toString().trim().equals("")) {
            DesignerToast.Error(getContext(), getContext().getString(R.string.scan_ot_add_id_no), Gravity.CENTER, Toast.LENGTH_SHORT);
            return;
        }

        String accountNumber = create_user_apply_loan_account_number.getText().toString();
        String phone = create_user_apply_loan_phone_number.getText().toString();
        String IDtext = create_user_apply_loan_id_card_number_txt.getText().toString(); //"12340011229"; //

        ArrayList<String> phoneList = new ArrayList<>();
        phoneList.add(phone);

//        CreateUserDetails userDetails = new CreateUserDetails(accountNumber,IDtext);
//        saveUserDetails(userDetails);
//
//        appUser.setAccountNumber(accountNumber);
//        appUser.setPhones(phoneList);
//        appUser.setDocumentID(IDtext);
//        saveUserInfo(appUser);

        NetworkProviderClass networkProviderClass = new NetworkProviderClass(getContext(),TAG);
        String network = networkProviderClass.getNetwork(phone);

        if (network == null)
            network = "unknown";

        if (network != null) {

            //Toast.makeText(getContext(), "Phone number network is " + network, Toast.LENGTH_SHORT).show();

            getNetworkWithPhone(IDtext, phone, network, accountNumber);

        } else {
            DesignerToast.Error(getContext(), "Phone number may be incorrect", Gravity.CENTER, Toast.LENGTH_SHORT);

        }

    }

    private void getNetworkWithPhone(String IDtext, final String phone, String networkGotten, String accountNumber) {
        AlertDialog.Builder alertName = new AlertDialog.Builder(getContext());
        final TextView TXTnetProviderGotten = new TextView(getContext());
        final EditText ETDgetNetProvider = new EditText(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(10,10,10,10);
        TXTnetProviderGotten.setLayoutParams(params);

        TXTnetProviderGotten.setText("Is your network provider '" + networkGotten.toUpperCase(Locale.getDefault()) + "'? If yes, click \"Accept\" else enter your network provider name");
        ETDgetNetProvider.setHint("enter network provider");
        alertName.setTitle("Network Provider");
        LinearLayout layoutName = new LinearLayout(getContext());
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(TXTnetProviderGotten); // displays the user input bar
        layoutName.addView(ETDgetNetProvider);
        alertName.setView(layoutName);

        alertName.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String txt; // variable to collect user input
                if (ETDgetNetProvider.getText().toString().equalsIgnoreCase("yes") || (ETDgetNetProvider.getText().toString().trim().equals(""))){

                    txt = networkGotten;
                }else{
                    txt = ETDgetNetProvider.getText().toString();
                }


                Log.d(TAG, "checkValidation: " + phone);

                ArrayList<HashMap<String, String>> phoneAndNetwork = new ArrayList<HashMap<String, String>>();

                HashMap<String, String> numMap = new HashMap<String, String>();
                try {
                    numMap.put("number", phone);
                    numMap.put("company", txt);

                    phoneAndNetwork.add(numMap);
                } catch (Exception e) {

                }


                customerModel = new CustomerModel(appUser.getOthernames(), appUser.getSurname(), IDtext, phoneAndNetwork, accountNumber);


                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());

                if (networkAvaillabilityClass.hasNetwork()){

                    // String actualNetwork = getNetworkWithPhone(networkGotten);

                    dialog.dismiss();

                    getToken();

                }else{
                    DesignerToast.Error(getContext(),"You may not be connected to the internet",Gravity.CENTER,Toast.LENGTH_SHORT);
                }
                //collectInput(); // analyze input (txt) in this method


            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel(); // closes dialog alertName.show() // display the dialog
            }
        });

        alertName.show();
    }

    private void getToken() {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getAuthClient().create(NLP_API_Interface.class);

        Call<AuthenticationResponse> call = nlp_api_interface.getAuthToken();

        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                if (response.code() == 200){
                    authenticationResponse = response.body();

                    Log.d(TAG, "onResponse: " + authenticationResponse.getToken());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    registerCustomer(authenticationResponse.getToken());

                }else {
                    DesignerToast.Error(getContext(),"oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        }));
    }

    // First Method call
    private void registerCustomer(String token) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        Call<CustomerModelResult> call = nlp_api_interface.createCustomer(customerModel, "Bearer " + token);


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<CustomerModelResult>() {

            @Override
            public void onResponse(Call<CustomerModelResult> call, Response<CustomerModelResult> response) {


                if (response.code() == 200) {
                    CustomerModelResult model = response.body();

                    Log.d(TAG, "onResponse: " + model.getDocument());
                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse: " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + model.getCustomerId());


                   // saveCustomerResult(model);

                    ArrayList<HashMap<String, String>> hashMaps = model.getPhones();

                    ArrayList<String> phones = new ArrayList<>();
                    for (int i = 0; i < hashMaps.size(); i++){
                        HashMap<String, String> hashMap = hashMaps.get(i);
                        phones.add(hashMap.get("number"));
                    }


                    AppUser new_appUser = new AppUser(model.getSurname(),model.getName(), phones,appUser.getEmailAddress(),appUser.getDate_of_birth(),appUser.getPassword(),model.getAccount(),appUser.isPhoneVerified(),model.getCustomerId(), model.getDocument(), appUser.getLoanApplicationID(), appUser.getLoanStatus(),appUser.getLast_login_datetime());

                    saveUserInfo(new_appUser);

                    callMainFragment();

//                    slideUpDown(dialog_replacement);
//                    showSecurityConcernDialog(appUser.getDocumentID());

                } else if (response.code() == 400) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                   // Toast.makeText(getContext(), errorResponse.getError(), Toast.LENGTH_SHORT).show();

                    if (errorResponse.getError().equals("Customer already registered.")) {

                        makeCustomerEnquiry();

                    } else {

                        String err_message = "Unknown Error";

                        if (errorResponse.getError() != null)
                            err_message = errorResponse.getError();

                        DesignerToast.Error(getContext(),err_message,Gravity.CENTER,Toast.LENGTH_SHORT);


                    }


                } else if (response.code() == 500) {

                    DesignerToast.Error(getContext(),"Something has gone wrong. Please try again later",Gravity.CENTER,Toast.LENGTH_SHORT);

                }

            }

            @Override
            public void onFailure(Call<CustomerModelResult> call, Throwable t) {



//                enableItems(true);
                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                DesignerToast.Error(getContext(),t.getMessage(),Gravity.CENTER,Toast.LENGTH_SHORT);
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));

    }

    private void makeCustomerEnquiry() {

        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        EnquiryModel enquiryModel = new EnquiryModel(customerModel.getDocument());
        Call<CustomerEnquiryResult> call = nlp_api_interface.makeCustomerEnquiry(enquiryModel, "Bearer " + authenticationResponse.getToken());


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<CustomerEnquiryResult>() {

            @Override
            public void onResponse(Call<CustomerEnquiryResult> call, Response<CustomerEnquiryResult> response) {

                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: code" + response.code());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    CustomerEnquiryResult enquiryResult = response.body();

                    Log.d(TAG, "onResponse: " + response.body().getActiveLoans().toString());
                    ArrayList<HashMap> activeLoanDetails = enquiryResult.getActiveLoans();
                    Log.d(TAG, "onResponse: " + activeLoanDetails.toString());
                    Log.d(TAG, "onResponse: " + enquiryResult.getCustomerId());

                    //appUser.setCustomerId(enquiryResult.getCustomerId());
                    saveCustomerEnquiry(enquiryResult);

//                    if (activeLoanDetails.size() > 0){
//                        HashMap eachLoanDetail = activeLoanDetails.get(0);
//                        Log.d(TAG, "onResponse: Hashmap : " + eachLoanDetail.toString());
//                        String status = "";
//                        try {
//                            status = (String) eachLoanDetail.get("status");
//                            Log.d(TAG, "onResponse: Status : " + status);
//                        } catch (Exception e) {
//                            Log.d(TAG, "onResponse: " + e.getMessage());
//                        }
//                        if (!status.equals("Ongoing")) {
//
//                            Log.d(TAG, "onResponse: Second Status " + status);
//
//
//                        } else {
//
//                            Gson gson = new Gson();
//                            Type type = new TypeToken<MyErrorClass>() {
//                            }.getType();
//                            MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);
//
//                            Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
//                            Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
//                            Log.d(TAG, "onResponse: " + response.code());
//
//                            DesignerToast.Error(getContext(),errorResponse.getError(),Gravity.CENTER,Toast.LENGTH_SHORT);
//
//                        }
//                    }else{
//                        callMainFragment();
//                    }

                    callMainFragment();
                } else {

                    DesignerToast.Error(getContext(),"Something has gone wrong, please try again later",Gravity.CENTER,Toast.LENGTH_SHORT);

                }


            }

            @Override
            public void onFailure(Call<CustomerEnquiryResult> call, Throwable t) {

                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        }));
    }



    public void checkPermissions() {
        PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if (!hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        } else {
            startCameraScanner();
        }
    }

    private void startCameraScanner() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        intent.setAction("com.google.zxing.client.android.SCAN");
        intent.putExtra("SAVE_HISTORY", false);
        startActivityForResult(intent, 0);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ALL) {
            if (!hasPermissions(getContext(), permissions)) {
                Toast.makeText(getContext(), "You need to allow all permissions to continue", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
            } else {
                startCameraScanner();
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(getContext(), contents, Toast.LENGTH_LONG).show();
                create_user_apply_loan_id_card_number_txt.setText(contents);
                Log.d(TAG, "onActivityResult: " + contents);
            } else {
                Toast.makeText(getContext(), "No data gotten", Toast.LENGTH_LONG).show();

            }

        }
    }

    private boolean validateField(EditText editText, String error_mesg) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(error_mesg);
            requestFocus(editText);

            return false;

        } else {
            editText.setError(null);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

//    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
//        if (sharedPreferences.contains(preferenceKey)) {
//            final Gson gson = new Gson();
//            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
//        }
//        return null;
//    }

    private void saveCustomerEnquiry(CustomerEnquiryResult enquiryResult) {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(enquiryResult);
        prefsEditor.putString(getString(R.string.customer_enquiry_result), json);
        prefsEditor.commit();
    }

    private void saveCustomerResult(CustomerModelResult model) {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString(getString(R.string.created_customer_result), json);
        prefsEditor.commit();
    }

    private void saveUserDetails(CreateUserDetails userDetails) {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userDetails);
        prefsEditor.putString(getString(R.string.created_user_details), json);
        prefsEditor.commit();
    }

    private AppUser retrieveUser() {

        SharedPreferences preferences = getContext().getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")) {

            return null;
        }

        return gson.fromJson(json, AppUser.class);
    }

    private void saveUserInfo(AppUser appUser) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appUser);
        prefsEditor.putString("app_user", json);
        prefsEditor.commit();
    }
}