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
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CreateUserDetails;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.AcceptScoringInput;
import com.mtechcomm.nanocreditnative.models.AcceptScoringModel;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DialogForPrivacyFragment extends Fragment {

    Button privacy_dialog_apply_dialog_get_started_btn;
    View view;
    private static final String TAG = DialogForPrivacyFragment.class.getSimpleName();
    AuthenticationResponse authenticationResponse;
    AppUser appUser;
    public DialogForPrivacyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog_for_privacy, container,false);

        privacy_dialog_apply_dialog_get_started_btn = view.findViewById(R.id.privacy_dialog_apply_dialog_get_started_btn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appUser = retrieveUser();
        privacy_dialog_apply_dialog_get_started_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });

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

                    acceptScoring(authenticationResponse.getToken());

                }else {
                    DesignerToast.Error(getContext(),"oops!!! please try again", Gravity.CENTER, Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                DesignerToast.Error(getContext(),"oops!!! please try again", Gravity.CENTER, Toast.LENGTH_SHORT);
            }
        }));
    }

    private void acceptScoring(String token) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        CreateUserDetails userDetails = getCustomerDetails();

        AcceptScoringInput acceptScoringInput = new AcceptScoringInput(userDetails.getScannedID());

        Call<AcceptScoringModel> call = nlp_api_interface.acceptScoring(acceptScoringInput, "Bearer " + token);


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<AcceptScoringModel>() {

            @Override
            public void onResponse(Call<AcceptScoringModel> call, Response<AcceptScoringModel> response) {


                if (response.code() == 200) {
                    AcceptScoringModel acceptScoringModel = response.body();

                    Log.d(TAG, "onResponse:  ret " + response.body());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse: App ID : " + acceptScoringModel.getApplicationId());

                    //saveApplyDetails(acceptScoringModel);
//                    appUser.setLoanApplicationID(acceptScoringModel.getApplicationId());

//                    saveUserInfo(appUser);

                    callMainFragment();

                    // Sixth Method Call
                    //getToken("applyforloan");
                } else {
                    try {

                        Log.d(TAG, "onResponse: " + response.errorBody().string());

                        Gson gson = new Gson();
                        Type type = new TypeToken<MyErrorClass>() {
                        }.getType();
                        MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                        Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: " + response.code());

                        DesignerToast.Error(getContext(),"Oops!!! " + errorResponse.getError(), Gravity.CENTER, Toast.LENGTH_SHORT);

//                        if (errorResponse.getError().equals("Customer with active loan.")){
//                            makeLoanEnquiry();
//                            //getToken("makeloanenquiry");
//                           // callMainFragment();
//                        }else{
//                            DesignerToast.Error(getContext(),errorResponse.getError(),Gravity.CENTER,Toast.LENGTH_SHORT);
//                        }


                    } catch (Exception e) {
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse:  err " + e.getMessage());
                        DesignerToast.Error(getContext(),"Oops!!! please try again", Gravity.CENTER, Toast.LENGTH_SHORT);
                    }

                }
            }

            @Override
            public void onFailure(Call<AcceptScoringModel> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                DesignerToast.Error(getContext(),"oops!!! please try again", Gravity.CENTER, Toast.LENGTH_SHORT);
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));
    }

    private void makeLoanEnquiry() {
    }


    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("apply_for_loan");
        }

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
}