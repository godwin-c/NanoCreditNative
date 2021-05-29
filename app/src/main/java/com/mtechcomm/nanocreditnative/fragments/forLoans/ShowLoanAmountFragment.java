package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtechcomm.nanocreditnative.MainActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.CheckScoreReadyResult;
import com.mtechcomm.nanocreditnative.models.ConfirmLoanModel;
import com.mtechcomm.nanocreditnative.models.LoanOfferModel;
import com.mtechcomm.nanocreditnative.models.LoanOfferResult;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.models.UpdateLoanDataModel;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.mtechcomm.nanocreditnative.services.UpdateLoanInfoService;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowLoanAmountFragment extends Fragment {

    TextView show_loan_amount_inst_text;
    EditText show_loan_amount_dialog_loan_amount_amount;
    Button show_loan_amount_dialog_loan_amount_btn_no, show_loan_amount_dialog_loan_amount_btn_yes;

    private static final String TAG = ShowLoanAmountFragment.class.getSimpleName();
    AuthenticationResponse authenticationResponse;
    int amount = 0;

    AppUser appUser;

    public ShowLoanAmountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                FragmentManager fm = getFragmentManager();
                if (fm != null) {
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                        Log.e( "Frag", fm.getBackStackEntryAt(0) + " is back" );

                    }

                    List<Fragment> fragList = fm.getFragments();
                    if (fragList != null && fragList.size() > 0) {
                        for (Fragment frag : fragList) {
                            if (frag == null) {
                                continue;
                            }
                            if (frag.isVisible()) {
                                Log.e( "Frag",frag.getTag()+" is Visible" );
                            }
                        }
                    }
                }


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_loan_amount, container, false);

        show_loan_amount_inst_text = view.findViewById(R.id.show_loan_amount_inst_text);
        show_loan_amount_dialog_loan_amount_amount = view.findViewById(R.id.show_loan_amount_dialog_loan_amount_amount);

        show_loan_amount_dialog_loan_amount_btn_no = view.findViewById(R.id.show_loan_amount_dialog_loan_amount_btn_no);
        show_loan_amount_dialog_loan_amount_btn_yes = view.findViewById(R.id.show_loan_amount_dialog_loan_amount_btn_yes);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckScoreReadyResult checkScoreReadyResult = getScoreready();
        show_loan_amount_inst_text.setText(getString(R.string.after_analysing_the_information_) + " " + checkScoreReadyResult.getAmount());

        show_loan_amount_dialog_loan_amount_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String entered_amount = show_loan_amount_dialog_loan_amount_amount.getText().toString();
                if (!entered_amount.equals("")) {

                    if (!entered_amount.matches("^[0-9]*$")){
                        DesignerToast.Error(getContext(), "Invalid Amount entered. Must be only numbers", Gravity.CENTER, Toast.LENGTH_SHORT);
                        return;
                    }

                        amount = Integer.valueOf(entered_amount);

                }

                if (amount > checkScoreReadyResult.getAmount()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle( "oops" )
                            .setIcon(R.drawable.ic_error_2)
                            .setMessage("Credit limit Exceeded!!!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                }
                            }).show();
                }else {
                    if (amount == 0)
                        amount = checkScoreReadyResult.getAmount();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Nano Credit")
                            .setMessage("Amount selected is " + amount)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                    getToken();
                                }
                            }).show();
                }

            }
        });

        show_loan_amount_dialog_loan_amount_btn_no.setOnClickListener(new View.OnClickListener() {
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
//                                Fragment parent = getParentFragment();
//                                parent.getFragmentManager().popBackStack();
//                                if(parent instanceof ApplyForLoanFragment) {
//                                    ((ApplyForLoanFragment) parent).changeFragment("score_ready");
//                                }
                                Fragment parent = getParentFragment();
                                if (((ApplyForLoanFragment) parent).getChildFragmentManager().getBackStackEntryCount() > 1){
                                    //Toast.makeText(getContext(),"e remain",Toast.LENGTH_SHORT).show();
                                    ((ApplyForLoanFragment) parent).getChildFragmentManager().popBackStack();
                                }
                            }
                        }).show();
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

                    //registerCustomer(authenticationResponse.getToken());
                    offerUserLoan();

                }else {
                    DesignerToast.Error(getContext(),"oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                DesignerToast.Error(getContext(),"oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));
    }

    // Fifteenth Method Call
    private void offerUserLoan() {

        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        CheckScoreReadyResult checkScoreReadyResult = getScoreready();

        LoanOfferModel loanOfferModel = new LoanOfferModel(amount, checkScoreReadyResult.getApplicationId());

        Call<LoanOfferResult> call = nlp_api_interface.offerLoan(loanOfferModel, "Bearer " + authenticationResponse.getToken());


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<LoanOfferResult>() {

            @Override
            public void onResponse(Call<LoanOfferResult> call, Response<LoanOfferResult> response) {


                if (response.code() == 200) {

                    //saveLoanOffer(model);

                    LoanOfferResult model = response.body();


                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Loan Processed")
                            .setMessage("please confirm your acceptance to take a loan of " + model.getAmount() + " from Nano Credit")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                    confirmLoan();
                                }
                            }).show();


                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {}.getType();
                    String error = "";
                    try {
                        MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                        error = errorResponse.getError();

                        Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: " + response.code());

                    }catch (Exception e){
                        error = e.getMessage();
                    }

                    DesignerToast.Error(getContext(),"Oops!!! " + error,Gravity.CENTER,Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<LoanOfferResult> call, Throwable t) {

                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                DesignerToast.Error(getContext(),"please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));

    }

    // Seventeenth Method Call
    private void confirmLoan() {

        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);

        CheckScoreReadyResult checkScoreReadyResult = getScoreready();

        //PriorityDataModelInput priorityDataModelInput = new PriorityDataModelInput(applicationId);
        ConfirmLoanModel confirmLoanModel = new ConfirmLoanModel(true, checkScoreReadyResult.getApplicationId());
        Call<LoanOfferResult> call = nlp_api_interface.confirmLoan(confirmLoanModel, "Bearer " + authenticationResponse.getToken());



        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<LoanOfferResult>() {

            @Override
            public void onResponse(Call<LoanOfferResult> call, Response<LoanOfferResult> response) {


                LoanOfferResult model = response.body();

                if (response.code() == 200) {

                    //saveLoanOffer(model);

                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    UpdateLoanDataModel updateLoanDataModel = new UpdateLoanDataModel(model.getApplicationId()+"",model.getStatus(),model.getCustomerId()+"",model.getAmount()+"",model.getStartDate(),model.getEndDate());

                    final Gson gson = new Gson();
                    String serializedObject = gson.toJson(updateLoanDataModel);

                    Intent intent = new Intent(getContext(), UpdateLoanInfoService.class);
                    intent.putExtra("data", serializedObject);
                    getContext().startService(intent);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle( "Congratulations !!!" )
                            .setMessage("Your loan has been confirmed and your account will be credited within 24 hours")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();


                                    ((MainActivity) getActivity()).finish();
                                    //updateUserLoanInfo(appUser.getLoanApplicationID());

//                                    HomeFragment homeFragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment");
////                                    ((menufeedActivity) getActivity()).setCountText(getCount);
//                                    homeFragment.setupViewPager();
//
//                                    getActivity().getFragmentManager().popBackStack();
                                }
                            }).show();


                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                   DesignerToast.Error(getContext(),"Oops!!! " + errorResponse.getError(),Gravity.CENTER,Toast.LENGTH_SHORT);

                }

            }

            @Override
            public void onFailure(Call<LoanOfferResult> call, Throwable t) {


                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());

                DesignerToast.Error(getContext(),"Oops!!! please try again",Gravity.CENTER,Toast.LENGTH_SHORT);

            }
        }));
    }

//    private void updateUserLoanInfo(int loanId){
//        NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());
//
//        if (networkAvaillabilityClass.hasNetwork()){
//
//
//            //UpdateUserDataModel updateUserDataModel = new UpdateUserDataModel(String.valueOf(customerID), appUser.getDocumentID(), appUser.getEmailAddress());
//            UpdateLoanDataModel updateUserDataModel = new UpdateLoanDataModel(String.valueOf(loanId),true);
//
//            Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
//            Call<UpdateUserDataResult> call = mtech_api_interface.updateLoanInfo(updateUserDataModel);
//            call.enqueue(new CustomCallBack<>(getContext(), new Callback<UpdateUserDataResult>() {
//                @Override
//                public void onResponse(Call<UpdateUserDataResult> call, Response<UpdateUserDataResult> response) {
//                    if (response.code() == 201){
//
//                        Log.d(TAG, "onResponse: " + response.body().getSuccess());
//                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
//                        Log.d(TAG, "onResponse: " + response.code());
//
//                        ((MainActivity) getActivity()).finish();
//                       // callMainFragment();
//
//                    }else {
//
//                        DesignerToast.Error(getContext(),"Oops!!! let's try again",Gravity.CENTER,Toast.LENGTH_SHORT);
//                        updateUserLoanInfo(appUser.getLoanApplicationID());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UpdateUserDataResult> call, Throwable t) {
//                    Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
//                    Log.d(TAG, "onFailure: " + t.getMessage());
//                }
//            }));
//
//        }else{
//            DesignerToast.Error(getContext(),"You may not be connected to the internet", Gravity.CENTER, Toast.LENGTH_SHORT);
//        }
//
//    }

    private CheckScoreReadyResult getScoreready() {
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(getString(R.string.customer_result_ready), "");
        if (json.equals("")) {
            return null;
        }
        CheckScoreReadyResult checkScoreReadyResult = gson.fromJson(json, CheckScoreReadyResult.class);

        return checkScoreReadyResult;
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