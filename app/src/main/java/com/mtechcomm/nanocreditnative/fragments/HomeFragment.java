package com.mtechcomm.nanocreditnative.fragments;


import android.animation.ArgbEvaluator;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtechcomm.nanocreditnative.MainActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.activities.DashboardActivity;
import com.mtechcomm.nanocreditnative.adapters.CardViewAdapter;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CreateUserDetails;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.classes.NetworkAvaillabilityClass;
import com.mtechcomm.nanocreditnative.models.AuthenticationResponse;
import com.mtechcomm.nanocreditnative.models.CardViewModel;
import com.mtechcomm.nanocreditnative.models.EnquiryModel;
import com.mtechcomm.nanocreditnative.models.LoanEnquiryModelResult;
import com.mtechcomm.nanocreditnative.models.LoanOfferResult;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.models.RepayLoanModel;
import com.mtechcomm.nanocreditnative.models.RepayLoanResponseModel;
import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CardViewAdapter adapter;
    List<CardViewModel> cardViewModels;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    LinearLayout applyLoan_layout, schedulePay_layout, discounts_layout, summary_layout, history_layout, year_to_date_layout, home_repay_loan;
    int PERMISSION_ALL = 1;
    TextView user_name_display, last_known_login;
    //    private String appId = "";
    CreateUserDetails userDetails;

    RepayLoanModel repayLoanModel;

    private static final String TAG = HomeFragment.class.getSimpleName();
    boolean dialogIshown = false;
    AppUser appUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void didVisibilityChange() {
        Activity activity = getActivity();
        if (isResumed()) {
            setupViewPager();
            // Once resumed and menu is visible, at last
            // our Fragment is really visible to user.
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        applyLoan_layout = (LinearLayout) view.findViewById(R.id.home_apply_loan);
        discounts_layout = (LinearLayout) view.findViewById(R.id.home_avail_discounts);
        schedulePay_layout = (LinearLayout) view.findViewById(R.id.home_pay_schedule);
        summary_layout = (LinearLayout) view.findViewById(R.id.home_loan_summary);
        history_layout = (LinearLayout) view.findViewById(R.id.home_loan_history);
        year_to_date_layout = (LinearLayout) view.findViewById(R.id.home_year_to_date);
        home_repay_loan = (LinearLayout) view.findViewById(R.id.home_repay_loan);

        user_name_display = (TextView) view.findViewById(R.id.user_name_display);
        last_known_login = (TextView) view.findViewById(R.id.last_known_login);

        viewPager = (ViewPager) view.findViewById(R.id.cardview_viewpager);

        home_repay_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //yetToBeImplemented();

                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());
                if (networkAvaillabilityClass.hasNetwork()) {
                    userDetails = getCustomerDetails();
                    if (userDetails != null) {
                        setupAmountDialog();

                        //getAuthToken("loan_summary", "loan_summary", userDetails.getScannedID());
                    } else {
                        DesignerToast.Error(getContext(), "Sorry, not enough information to proceed!!!", Gravity.CENTER, Toast.LENGTH_SHORT);
                    }

                } else {
                    DesignerToast.Error(getContext(), "no internet connection", Gravity.CENTER, Toast.LENGTH_SHORT);
                }
            }
        });
        year_to_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callMainActivity("year_to_date");
                yetToBeImplemented();
            }
        });
        history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callMainActivity("loan_history");
                yetToBeImplemented();
            }
        });
        summary_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // yetToBeImplemented();
                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());
                if (networkAvaillabilityClass.hasNetwork()) {
                    userDetails = getCustomerDetails();
                    if (userDetails != null) {
                        getAuthToken("loan_summary", "loan_summary", userDetails.getScannedID());
                    } else {
                        DesignerToast.Error(getContext(), "Sorry, not enough information to check!!!", Gravity.CENTER, Toast.LENGTH_SHORT);
                    }

                } else {
                    DesignerToast.Error(getContext(), "no internet connection", Gravity.CENTER, Toast.LENGTH_SHORT);
                }
            }
        });
        schedulePay_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callMainActivity("loan_payment");
                yetToBeImplemented();
            }
        });
        discounts_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callMainActivity("availlable_discounts");
                yetToBeImplemented();
            }
        });
        // Setup OnClick listener
        applyLoan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity("apply_for_loan");

            }
        });


        appUser = retrieveUser();

        setupNavHeaderOnMainActivity(appUser);
        user_name_display.setText(appUser.getSurname() + " " + appUser.getOthernames());

        String lastlogin = appUser.getLast_login_datetime(); retrieveLastLogin();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss aaa");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(lastlogin);
            last_known_login.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            last_known_login.setText(lastlogin);
        }



        //setupViewPager();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupAmountDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loan_amount, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        TextView textView = (TextView)dialogView.findViewById(R.id.inst_text);

        textView.setText("Enter the Desired amount to Repay");
        TextInputEditText editText = (TextInputEditText) dialogView.findViewById(R.id.dialog_loan_amount_amount);

        Button dialog_loan_amount_btn_no = (Button)dialogView.findViewById(R.id.dialog_loan_amount_btn_no);
        Button dialog_loan_amount_btn_yes = (Button)dialogView.findViewById(R.id.dialog_loan_amount_btn_yes);

        dialog_loan_amount_btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        //editText.setText("test label");


        dialog_loan_amount_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Amount supplied : " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                String amountInString = editText.getText().toString().trim();

                if (amountInString.matches("")) {
                    Toast.makeText(getContext(), "You haven't entered any amount yet", Toast.LENGTH_SHORT).show();
                    return;
                }

                repayLoanModel = new RepayLoanModel(Integer.valueOf(amountInString),appUser.getLoanApplicationID(),Integer.valueOf(userDetails.getScannedID()));
                Log.d(TAG, "onClick: " + appUser.getLoanApplicationID());

                //getAuthToken("loan_repay", "loan_repay", userDetails.getScannedID());
                alertDialog.dismiss();
            }
        });



        alertDialog.show();
    }

    private void getAuthToken(String source, String callingClass, String documentId) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getAuthClient().create(NLP_API_Interface.class);

        Call<AuthenticationResponse> call = nlp_api_interface.getAuthToken();
        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                AuthenticationResponse authenticationResponse = response.body();
                Log.d(TAG, "onResponse: " + authenticationResponse.getToken());
                Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onResponse: " + response.code());

                if (response.code() == 200) {

                    //String test = "test";
                    switch (source) {
                        case "loan_summary":
                            makeLoanEnquiry(authenticationResponse.getToken(), callingClass, documentId);
                            break;
                        case "loan_repay":
                            makeLoanRepayment(authenticationResponse.getToken(), repayLoanModel);
                            break;
                    }
                }else {
                    DesignerToast.Error(getContext(),"Please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                DesignerToast.Error(getContext(),"" + t.getMessage() + ". Please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));
    }

    private void makeLoanRepayment(String token, RepayLoanModel repayLoanModel) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);
        Call<RepayLoanResponseModel> call = nlp_api_interface.repayLoan(repayLoanModel,"Bearer " + token);

        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<RepayLoanResponseModel>() {
            @Override
            public void onResponse(Call<RepayLoanResponseModel> call, Response<RepayLoanResponseModel> response) {
                Log.e(TAG, "onResponse: Error Code " + response.code() );
                if (response.code() == 200) {

                    DesignerToast.Info(getContext(), "Your Loan repayment has been sent as '" + response.body().getTypePayment() + "'", Gravity.CENTER, Toast.LENGTH_SHORT);

                } else {
                    //DesignerToast.Info(getContext(), "You Loan status is '" + status + "'", Gravity.CENTER, Toast.LENGTH_SHORT);
                    String error = "Something has gone wrong!!!";

                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();

                    MyErrorClass errorResponse = null;
                    try {
                       errorResponse  = gson.fromJson(response.errorBody().charStream(), type);

                        if (errorResponse.getError() != null)
                            error = errorResponse.getError();

                        Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    }catch (Exception e){
                        Log.d(TAG, "onResponse: Error ret : " + e.getMessage());
                    }



                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());



                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("oops")
                            .setIcon(R.drawable.ic_error_2)
                            .setMessage(error)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<RepayLoanResponseModel> call, Throwable t) {
                DesignerToast.Error(getContext(),"Oops!!! something has gone wrong", Gravity.CENTER, Toast.LENGTH_SHORT);
                Log.d(TAG, "onResponse:  ret " + t.getMessage());
                Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
            }
        }));
    }

    private void makeLoanEnquiry(String token, String callingClass, String documentId) {
        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);


        EnquiryModel enquiryModel = new EnquiryModel(documentId);

        Call<LoanEnquiryModelResult> call = nlp_api_interface.makeLoanEnquiry(enquiryModel, "Bearer " + token);


        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<LoanEnquiryModelResult>() {

            @Override
            public void onResponse(Call<LoanEnquiryModelResult> call, Response<LoanEnquiryModelResult> response) {

                if (response.code() == 200) {

                    LoanEnquiryModelResult enquiryResult = response.body();

                    if(enquiryResult.getStatus() != null){


                        Log.d(TAG, "onResponse: code " + response.code());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: ret " + enquiryResult.getApplicationId());
                        Log.d(TAG, "onResponse: ret " + enquiryResult.getStatus());

                        if (callingClass.equals("setupView")){
                            saveLoanEnquiry(enquiryResult);

                            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));
                            cardViewModels.add(new CardViewModel(enquiryResult.getStatus(), enquiryResult.getAmount() + ""));
                            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));

                            setupViewForLoan();
                        }else {
                            String status = enquiryResult.getStatus();
                            if (!status.equals("Draft") && (!status.equals("Offer")) && (!status.equals("Cancelled"))) {
                                saveLoanEnquiry(enquiryResult);
                                callMainActivity("loan_summary");
                            } else {
                                DesignerToast.Info(getContext(), "You Loan status is '" + status + "'", Gravity.CENTER, Toast.LENGTH_SHORT);
                            }
                        }


                    }else{
                        DesignerToast.Info(getContext(), "Unfortunately, something has gone wrong. Please try again", Gravity.CENTER, Toast.LENGTH_SHORT);
                    }

//                    appId = String.valueOf(enquiryResult.getAppId());
//
//                    appUser.setLoanApplicationID(enquiryResult.getAppId());
//
//                    saveUserInfo(appUser);
//                    getToken("applyforloan");

                } else {

                    if (callingClass.equals("setupView")){
                        cardViewModels.add(new CardViewModel("Non-Active", "No non-active Loans"));
                        cardViewModels.add(new CardViewModel("Active", "No active loans"));
                        cardViewModels.add(new CardViewModel("Non-Active", "No non-active loans"));

                        setupViewForLoan();
                    }else {

                        String error = "Unfortunately, something has gone wrong. Please try again";

                        Gson gson = new Gson();
                        Type type = new TypeToken<MyErrorClass>() {
                        }.getType();
                        MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                        Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                        Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                        Log.d(TAG, "onResponse: " + response.code());

                        if (errorResponse.getError() != null)
                            error = errorResponse.getError();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setTitle("oops")
                                .setIcon(R.drawable.ic_error_2)
                                .setMessage(error)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        dialoginterface.dismiss();
                                    }
                                }).show();
                    }

//                    new SweetAlertDialog(getContext())
//                            .setTitleText("Ooops !!!")
//                            .setContentText(errorResponse.getError())
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
                }


            }

            @Override
            public void onFailure(Call<LoanEnquiryModelResult> call, Throwable t) {

                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onFailure: " + t.getMessage());
                Log.d(TAG, "onFailure: ");

                if (callingClass.equals("setupView")){
                    cardViewModels.add(new CardViewModel("Non-Active", "No non-active Loans"));
                    cardViewModels.add(new CardViewModel("Active", "No active loans"));
                    cardViewModels.add(new CardViewModel("Non-Active", "No non-active loans"));

                    setupViewForLoan();
                }else {
                    DesignerToast.Error(getContext(),"Oops!!!, please try again",Gravity.CENTER,Toast.LENGTH_SHORT);
                }



            }
        }));
    }

    private CreateUserDetails getCustomerDetails() {
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(getString(R.string.created_user_details), "");
        if (json.equals("")) {
            return null;
        }
        CreateUserDetails createUserDetails = gson.fromJson(json, CreateUserDetails.class);

        return createUserDetails;
    }

    private void saveLoanEnquiry(LoanEnquiryModelResult enquiryResult) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_loan_offer", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(enquiryResult);
        prefsEditor.putString("customer_loan", json);
        prefsEditor.commit();

        callMainActivity("loan_summary");

    }

    private void setupNavHeaderOnMainActivity(AppUser appUser) {

        ((DashboardActivity) getActivity()).setupNavHeader(appUser);
    }

    public void setupViewPager() {

        cardViewModels = new ArrayList<>();

        appUser = retrieveUser();
        //LoanOfferResult loanOfferResult = retrieveLoanInfo();

        if (appUser.getLoanStartDate() != null){
            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));
            cardViewModels.add(new CardViewModel(appUser.getLoanStatus(), appUser.getLoanAmount() + ""));
            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));

            setupViewForLoan();

        }else {
            if (appUser.getLoanStatus().equals("t") || appUser.getLoanStatus().equals("true")){
                if (appUser.getDocumentID() != null && !appUser.getDocumentID().equals("")){
                    getAuthToken("loan_summary", "setupView", appUser.getDocumentID());
                }else {
                    cardViewModels.add(new CardViewModel("Non-Active", "No non-active Loans"));
                    cardViewModels.add(new CardViewModel("Active", "No active loans"));
                    cardViewModels.add(new CardViewModel("Non-Active", "No non-active loans"));

                    setupViewForLoan();
                }

            }else {
                cardViewModels.add(new CardViewModel("Non-Active", "No non-active Loans"));
                cardViewModels.add(new CardViewModel("Active", "No active loans"));
                cardViewModels.add(new CardViewModel("Non-Active", "No non-active loans"));

                setupViewForLoan();

            }
        }
//        if (appUser.getLoanStatus() == null) {
//            cardViewModels.add(new CardViewModel("Non-Active", "No non-active Loans"));
//            cardViewModels.add(new CardViewModel("Active", "No active loans"));
//            cardViewModels.add(new CardViewModel("Non-Active", "No non-active loans"));
//        } else {
//            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));
//            cardViewModels.add(new CardViewModel(appUser.getLoanStatus(), appUser.getLoanAmount() + ""));
//            cardViewModels.add(new CardViewModel("Non-Active", "- - -"));
//        }



    }

    private  void setupViewForLoan(){
        adapter = new CardViewAdapter(cardViewModels, getContext());


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setPadding(130, 0, 130, 20);

        colors = new Integer[]{
                Objects.requireNonNull(getContext()).getResources().getColor(R.color.white),
                getContext().getResources().getColor(R.color.white),
                getContext().getResources().getColor(R.color.white),
        };

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private boolean logoutRequested() {
        SharedPreferences preferences = getActivity().getSharedPreferences("logout_requested", Context.MODE_PRIVATE);

        return preferences.getBoolean("logout_request", false);
    }


    private void yetToBeImplemented() {
        DesignerToast.Info(getContext(), "you do not have much activities yet", Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    private void callMainActivity(String fragmentName) {

        storeChoice(fragmentName);

        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);

        getActivity().startActivity(intent);

    }

    private void storeChoice(String choice) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_choice", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("fragment_id", choice);
        prefsEditor.commit();
    }

    protected void showDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.apply_loan_info_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final Button get_started_btn = (Button) promptView.findViewById(R.id.apply_dialog_get_started_btn);

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        get_started_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSION_ALL) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    private LoanOfferResult retrieveLoanInfo() {

        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_loan_offer", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("customer_loan_offer", "");
        if (json.equals("")) {
            return null;
        }
        LoanOfferResult loanOfferResult = gson.fromJson(json, LoanOfferResult.class);

        return loanOfferResult;
    }

    private AppUser retrieveUser() {
        SharedPreferences preferences = getContext().getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")) {
            return null;
        }
        AppUser appUser = gson.fromJson(json, AppUser.class);

        return appUser;
    }

    private String retrieveLastLogin() {
        SharedPreferences preferences = getContext().getSharedPreferences("last_login", Context.MODE_PRIVATE);
        String date = preferences.getString("last_login", "");

        if (date.equals("")) {
            date = getDateAndTime();
        }
        return date;
    }

    private String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss aaa");
        String todaysDate = (sdf.format(cal.getTime()));
        return todaysDate;
    }

    @Override
    public void onResume() {
        super.onResume();
        didVisibilityChange();
    }


}
