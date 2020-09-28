package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.CreateUserDetails;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.LoanModel;
import com.mtechcomm.nanocreditnative.models.LoanModelResult;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestForLoanFragment extends Fragment {

    private static final String TAG = RequestForLoanFragment.class.getSimpleName();
    Button display_created_user_btn_retry_loan_create;

    public RequestForLoanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_request_for_loan, container, false);
        display_created_user_btn_retry_loan_create = view.findViewById(R.id.display_created_user_btn_retry_loan_create);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getToken();

        display_created_user_btn_retry_loan_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });
    }

    private void getToken() {

        if (display_created_user_btn_retry_loan_create.getVisibility() == View.VISIBLE ){
            display_created_user_btn_retry_loan_create.setVisibility(View.GONE);
        }


        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getAuthClient().create(NLP_API_Interface.class);

        Call<AuthenticationResponse> call = nlp_api_interface.getAuthToken();

        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                if (response.code() == 200){
                    AuthenticationResponse authenticationResponse = response.body();

                    Log.d(TAG, "onResponse: " + authenticationResponse.getToken());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    sendLoanApplication(authenticationResponse.getToken());

                }else {
                    DesignerToast.Error(getContext(),"oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                DesignerToast.Error(getContext(),t.getMessage(),Gravity.CENTER,Toast.LENGTH_SHORT);

                display_created_user_btn_retry_loan_create.setVisibility(View.VISIBLE);

            }
        }));
    }


    // Seventh Method Call
    private void sendLoanApplication(String token) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        CreateUserDetails userDetails = getCustomerDetails();
        LoanModel loanModel = new LoanModel(userDetails.getScannedID());

        Call<LoanModelResult> call = nlp_api_interface.requestLoan(loanModel, "Bearer " + token);


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<LoanModelResult>() {

            @Override
            public void onResponse(Call<LoanModelResult> call, Response<LoanModelResult> response) {


                if (response.code() == 200) {
                    LoanModelResult loanModelResult = response.body();

                    Log.d(TAG, "onResponse:  ret " + response.body());
                    Log.d(TAG, "onResponse:  ret AppID " + loanModelResult.getApplicationId());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    saveCustomerLoanDetails(loanModelResult);
                    callMainFragment();

                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    DesignerToast.Error(getContext(),errorResponse.getError(), Gravity.CENTER, Toast.LENGTH_SHORT);

                    display_created_user_btn_retry_loan_create.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<LoanModelResult> call, Throwable t) {

//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                display_created_user_btn_retry_loan_create.setVisibility(View.VISIBLE);
            }
        }));

    }

    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("before_credit_score");
        }

    }

    private CreateUserDetails getCustomerDetails(){
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(getString(R.string.created_user_details), "");
        if (json.equals("")){
            return  null;
        }
        CreateUserDetails createUserDetails = gson.fromJson(json, CreateUserDetails.class);

        return createUserDetails;
    }

    private void saveCustomerLoanDetails(LoanModelResult model) {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString(getString(R.string.customer_loan_details_save), json);
        prefsEditor.commit();
    }
}