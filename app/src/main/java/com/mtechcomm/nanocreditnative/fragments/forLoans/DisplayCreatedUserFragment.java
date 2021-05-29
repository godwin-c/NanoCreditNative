package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.classes.NetworkAvaillabilityClass;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.UpdateUserDataModel;
import com.mtechcomm.nanocreditnative.models.UpdateUserDataResult;
import com.mtechcomm.nanocreditnative.net.Mtech_API_Interface;
import com.mtechcomm.nanocreditnative.net.Mtech_API_client;
import com.vdx.designertoast.DesignerToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayCreatedUserFragment extends Fragment {

    TextView display_created_user_customer_created_name, display_created_user_customer_created_doc,
            display_created_user_customer_created_ID, display_created_user_btn_cus_created_next;

    View view;
    AppUser appUser;
    int customerID = 0;

    private static final String TAG = DisplayCreatedUserFragment.class.getSimpleName();

    public DisplayCreatedUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_created_user,container, false);

        display_created_user_btn_cus_created_next = view.findViewById(R.id.display_created_user_btn_cus_created_next);
        display_created_user_customer_created_ID = view.findViewById(R.id.display_created_user_customer_created_ID);
        display_created_user_customer_created_doc = view.findViewById(R.id.display_created_user_customer_created_doc);
        display_created_user_customer_created_name = view.findViewById(R.id.display_created_user_customer_created_name);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appUser = retrieveUser();

//        CustomerModelResult customerModelResult = getCustomerModelResult();
//        CustomerEnquiryResult customerEnquiryResult = getCustomerEnquired();

        String name = appUser.getSurname() + " " + appUser.getOthernames();
        //String idNumber = "";

        customerID = appUser.getCustomerId();
//        if (customerModelResult != null){
//            //idNumber = customerModelResult.getDocument();
//            customerID = customerModelResult.getCustomerId();
//           // source = "customerModel";
//        }else if(customerEnquiryResult != null){
//            //idNumber = customerEnquiryResult.
//            customerID = customerEnquiryResult.getCustomerId();
//           // source = "customerEnquiry";
//        }

        display_created_user_customer_created_name.setText(name);
        display_created_user_customer_created_ID.setText("Customer ID : " + customerID );

        display_created_user_btn_cus_created_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    private void updateUserInfo(){
        NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());

        if (networkAvaillabilityClass.hasNetwork()){


            UpdateUserDataModel updateUserDataModel = new UpdateUserDataModel(String.valueOf(customerID), appUser.getDocumentID(),appUser.getAccountNumber(), appUser.getEmailAddress());
           // Log.d(TAG, "UpdateUserModel:  ret " + getClass().getEnclosingMethod().getName());
            Log.d(TAG, "UpdateUserModel: " + updateUserDataModel.getDocid());

            Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
            Call<UpdateUserDataResult> call = mtech_api_interface.updateUserData(updateUserDataModel);
            call.enqueue(new CustomCallBack<>(getContext(), new Callback<UpdateUserDataResult>() {
                @Override
                public void onResponse(Call<UpdateUserDataResult> call, Response<UpdateUserDataResult> response) {
                    if (response.code() == 201){

                        Log.d(TAG, "onResponse: " + response.body().getSuccess());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: " + response.code());

                        callMainFragment();

                    }else {
                        Log.d(TAG, "onResponse: " + response.body().toString());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: " + response.code());

                        DesignerToast.Error(getContext(),"Oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserDataResult> call, Throwable t) {
                    Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            }));

        }else{
            DesignerToast.Error(getContext(),"You may not be connected to the internet", Gravity.CENTER, Toast.LENGTH_SHORT);
        }

    }
    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("show_privacy_view");
        }

    }

    private AppUser retrieveUser(){
        SharedPreferences preferences = getContext().getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")){
            return  null;
        }
        AppUser appUser = gson.fromJson(json, AppUser.class);

        return appUser;
    }

//    private CustomerEnquiryResult getCustomerEnquired(){
//        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String json = preferences.getString(getString(R.string.customer_enquiry_result), "");
//        if (json.equals("")){
//            return  null;
//        }
//        CustomerEnquiryResult customerEnquiryResult = gson.fromJson(json, CustomerEnquiryResult.class);
//
//        return customerEnquiryResult;
//    }

//    private CustomerModelResult getCustomerModelResult(){
//        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String json = preferences.getString(getString(R.string.created_customer_result), "");
//        if (json.equals("")){
//            return  null;
//        }
//        CustomerModelResult customerModelResult = gson.fromJson(json, CustomerModelResult.class);
//
//        return customerModelResult;
//    }
}