package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gusakov.library.PulseCountDown;
import com.gusakov.library.java.interfaces.OnCountdownCompleted;
import com.mtechcomm.nanocreditnative.MainActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.CheckScoreReadyResult;
import com.mtechcomm.nanocreditnative.models.LoanModelResult;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.models.PriorityDataModelInput;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BeforePriorityDataFragment extends Fragment {

    TextView before_priority_data_proceed_to_show_loan_app_info_customer_name, before_priority_data_proceed_to_show_loan_app_info_loan_id,
            before_priority_data_txt_permission;

    PulseCountDown pulseCountDown;
    Button before_priority_data_proceed_to_show_loan_app_info_cancel, before_priority_data_proceed_to_show_loan_app_info_continue_to_apply;

    LinearLayout before_priority_data_linear_layout;

    private static final String TAG = BeforePriorityDataFragment.class.getSimpleName();

    boolean priority_data_sent;
    AuthenticationResponse authenticationResponse;

    AppUser appUser;
    LoanModelResult loanModelResult;

    public BeforePriorityDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_priority_data, container, false);

        before_priority_data_proceed_to_show_loan_app_info_customer_name = view.findViewById(R.id.before_priority_data_proceed_to_show_loan_app_info_customer_name);
        before_priority_data_proceed_to_show_loan_app_info_loan_id = view.findViewById(R.id.before_priority_data_proceed_to_show_loan_app_info_loan_id);
        before_priority_data_txt_permission = view.findViewById(R.id.before_priority_data_txt_permission);

        pulseCountDown = view.findViewById(R.id.pulseCountDown);

        before_priority_data_proceed_to_show_loan_app_info_cancel = view.findViewById(R.id.before_priority_data_proceed_to_show_loan_app_info_cancel);
        before_priority_data_proceed_to_show_loan_app_info_continue_to_apply = view.findViewById(R.id.before_priority_data_proceed_to_show_loan_app_info_continue_to_apply);

        before_priority_data_linear_layout = view.findViewById(R.id.before_priority_data_linear_layout);

        priority_data_sent = false;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appUser = retrieveUser();
        loanModelResult = getCustomerLoanDetails();

        before_priority_data_proceed_to_show_loan_app_info_customer_name.setText(appUser.getOthernames() + " " + appUser.getSurname());
        before_priority_data_proceed_to_show_loan_app_info_loan_id.setText("Loan Application ID: " + loanModelResult.getApplicationId());
        before_priority_data_proceed_to_show_loan_app_info_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Nano Credit")
                        .setMessage("Cancel loan Application?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
//                                getActivity().getFragmentManager().popBackStack();
                                ((MainActivity) getActivity()).onBackPressed();
                            }
                        }).show();
            }
        });
        before_priority_data_proceed_to_show_loan_app_info_continue_to_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!priority_data_sent){
                    getToken();
                }else {

                    checkIfScoreReady(loanModelResult.getApplicationId(), authenticationResponse.getToken());
                }
            }
        });

        pulseCountDown.start(new OnCountdownCompleted() {
            @Override
            public void completed() {
                before_priority_data_txt_permission.setVisibility(View.VISIBLE);
                before_priority_data_linear_layout.setVisibility(View.VISIBLE);
                pulseCountDown.setVisibility(View.GONE);
            }
        });
    }

    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("score_ready");
        }

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

                    //sendLoanApplication(authenticationResponse.getToken());
//                    LoanModelResult loanModelResult = getCustomerLoanDetails();
                    callPriorityData(loanModelResult.getApplicationId(),authenticationResponse.getToken());

                }else {
                    DesignerToast.Error(getContext(),"oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                DesignerToast.Error(getContext(),t.getMessage(),Gravity.CENTER,Toast.LENGTH_SHORT);

            }
        }));
    }

    // Eleventh Method Call
    private void callPriorityData(int applicationId, String token) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        PriorityDataModelInput priorityDataModelInput = new PriorityDataModelInput(applicationId);
        Call<Void> call = nlp_api_interface.setPriorityData(priorityDataModelInput, "Bearer " + token);

        // nlp_api_interface.setPriorityData(priorityDataModelInput, "Bearer " + token);

        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {

                    // Twelveth Method Call
                    priority_data_sent = true;

//                    loanModelResult = getCustomerLoanDetails();
                    checkIfScoreReady(loanModelResult.getApplicationId(), authenticationResponse.getToken());

                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    DesignerToast.Error(getContext(),errorResponse.getError() + " Try again", Gravity.CENTER,Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                DesignerToast.Error(getContext(),t.getMessage(), Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));

    }

    // Thirteenth Method Call
    private void checkIfScoreReady(int applicationId, String token) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);


        PriorityDataModelInput priorityDataModelInput = new PriorityDataModelInput(applicationId);
        Call<CheckScoreReadyResult> call = nlp_api_interface.checkScoreReady(priorityDataModelInput, "Bearer " + token);

        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<CheckScoreReadyResult>() {

            @Override
            public void onResponse(Call<CheckScoreReadyResult> call, Response<CheckScoreReadyResult> response) {


                if (response.code() == 200) {

                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse:  ret " + response.body().getAmount());

                    CheckScoreReadyResult scoreReadyResult = response.body();

                    saveScoreReadyResult(scoreReadyResult);
                    //showAmountGotten(scoreReadyResult);
                    callMainFragment();

                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                   DesignerToast.Error(getContext(),errorResponse.getError(),Gravity.CENTER,Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<CheckScoreReadyResult> call, Throwable t) {
                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                DesignerToast.Error(getContext(),t.getMessage(),Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));
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

    private LoanModelResult getCustomerLoanDetails(){
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(getString(R.string.customer_loan_details_save), "");
        if (json.equals("")){
            return  null;
        }

        return gson.fromJson(json, LoanModelResult.class);
    }

    private void saveScoreReadyResult(CheckScoreReadyResult model) {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString(getString(R.string.customer_result_ready), json);
        prefsEditor.commit();
    }
}