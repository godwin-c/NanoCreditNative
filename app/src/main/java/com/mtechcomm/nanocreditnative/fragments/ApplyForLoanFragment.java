package com.mtechcomm.nanocreditnative.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.RunningServiceClass;
import com.mtechcomm.nanocreditnative.fragments.forLoans.BeforeCreditScoreFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.BeforePriorityDataFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.CreateUserFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.DialogForPrivacyFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.DisplayCreatedUserFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.RequestForLoanFragment;
import com.mtechcomm.nanocreditnative.fragments.forLoans.ShowLoanAmountFragment;
import com.mtechcomm.nanocreditnative.models.CustomerModel;
import com.mtechcomm.nanocreditnative.models.CustomerModelResult;
import com.mtechcomm.nanocreditnative.models.LoanModelResult;
import com.mtechcomm.nanocreditnative.models.LoanOfferResult;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyForLoanFragment extends Fragment {

//    EditText bank_account_num_EDT, phone_number_EDT, scanned_ID_TXT;
//    //    EditText bank_name_EDT
//    Button scan_ID_Card_BTN, submit_BTN, continue_to_apply, get_started_btn, proceed_to_credit_score_btn_no, proceed_to_credit_score_btn_yes, proceed_to_show_loan_app_info_cancel, proceed_to_show_loan_app_info_continue_to_apply;
//    TextView  proceed_to_show_loan_app_info_customer_name, proceed_to_show_loan_app_info_customer_id;
//
//    LinearLayout first_view, proceed_to_show_loan_app_info, customer_created_view, dialog_replacement, proceed_to_credit_score;
//
//    CustomerModel customerModel;
    AppUser appUser;

//    int PERMISSION_ALL = 11110;
//    String[] PERMISSIONS;
//
//   private String appId = "";
//   private String partnerScriptID = "";

//    LoanModelResult loanModelResult;
//    CheckScoreReadyResult scoreReadyResult;
//
//    private ProgressDialog mProgressDialog;

    private static final String TAG = ApplyForLoanFragment.class.getSimpleName();

    FrameLayout child_fragment_container;

    public ApplyForLoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_for_loan, container, false);

        child_fragment_container = view.findViewById(R.id.child_fragment_container);

//        bank_account_num_EDT = (EditText) view.findViewById(R.id.apply_loan_account_number);
//
//        phone_number_EDT = (EditText) view.findViewById(R.id.apply_loan_phone_number);
//        scan_ID_Card_BTN = (Button) view.findViewById(R.id.apply_loan_btn_scan_id);
//        submit_BTN = (Button) view.findViewById(R.id.apply_loan_submit);
//
//        proceed_to_show_loan_app_info_customer_id = (TextView) view.findViewById(R.id.proceed_to_show_loan_app_info_customer_id);
//        proceed_to_show_loan_app_info_customer_name = (TextView) view.findViewById(R.id.proceed_to_show_loan_app_info_customer_name);
//        proceed_to_show_loan_app_info_continue_to_apply = (Button) view.findViewById(R.id.proceed_to_show_loan_app_info_continue_to_apply);
//        proceed_to_show_loan_app_info_cancel = view.findViewById(R.id.proceed_to_show_loan_app_info_cancel);
//
//        first_view = (LinearLayout) view.findViewById(R.id.first_view);
//        proceed_to_show_loan_app_info = (LinearLayout) view.findViewById(R.id.proceed_to_show_loan_app_info);
//        customer_created_view = (LinearLayout) view.findViewById(R.id.third_screen);
//        scanned_ID_TXT = view.findViewById(R.id.apply_loan_id_card_number_txt);
//
//        dialog_replacement = view.findViewById(R.id.dialog_replacement);
//        get_started_btn = view.findViewById(R.id.apply_dialog_get_started_btn);
//
//        proceed_to_credit_score = view.findViewById(R.id.proceed_to_credit_score);
//        proceed_to_credit_score_btn_no = view.findViewById(R.id.proceed_to_credit_score_btn_no);
//        proceed_to_credit_score_btn_yes = view.findViewById(R.id.proceed_to_credit_score_btn_yes);

        // Inflate the layout for this fragment
        return view;
    }

    private void setupFragmentToDisplay(Class fragmentClass) {
        Fragment fragment = null;
        try {

            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {

            e.printStackTrace();

        }

        assert fragment != null;
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;


// Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getChildFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag((fragmentTag)) == null) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.sliding_in_left, R.anim.sliding_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                    .replace(R.id.child_fragment_container, fragment)
                    .addToBackStack(fragment.getTag()).commit();
        }

        // Insert the fragment by replacing any existing fragment

//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        fragmentManager.beginTransaction().replace(R.id.dashboardContent, fragment).commit();


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFragmentToDisplay(CreateUserFragment.class);

    }


//    private void enquireAboutLoan(String documentID, String token) {
//
//
//        NLP_API_Interface nlp_api_interface = NLP_Api_Client.getClient().create(NLP_API_Interface.class);
//
//        EnquiryModel enquiryModel = new EnquiryModel(documentID);
//        Call<LoanEnquiryModelResult> call = nlp_api_interface.makeLoanEnquiry(enquiryModel, "Bearer " + token);
//
//
//        call.enqueue(new CustomCallBack<>(getActivity(), new Callback<LoanEnquiryModelResult>() {
//
//            @Override
//            public void onResponse(Call<LoanEnquiryModelResult> call, Response<LoanEnquiryModelResult> response) {
//
//                if (response.code() == 200) {
//
//                    LoanEnquiryModelResult enquiryResult = response.body();
//
//                    Log.d(TAG, "onResponse: code" + response.code());
//                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
//                    Log.d(TAG, "onResponse: ret " + enquiryResult.getAppId());
//
//                    appId = String.valueOf(enquiryResult.getAppId());
//
//                   appUser.setLoanApplicationID(enquiryResult.getAppId());
//
//                   saveUserInfo(appUser);
//                    getToken("applyforloan");
//
//                } else {
//
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<MyErrorClass>() {
//                    }.getType();
//                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);
//
//                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
//                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
//                    Log.d(TAG, "onResponse: " + response.code());
//
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
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<LoanEnquiryModelResult> call, Throwable t) {
//
//                Log.d(TAG, "onFailure:  ret " + getClass().getEnclosingMethod().getName());
//                Log.d(TAG, "onFailure: " + t.getMessage());
//                Log.d(TAG, "onFailure: ");
//
//            }
//        }));
//    }



//    private void showAmountGotten(CheckScoreReadyResult scoreResult) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_loan_amount, null);
//        builder.setView(dialogView);
//
//        final Button dismissButton = dialogView.findViewById(R.id.dialog_loan_amount_btn_no);
//        final Button accept = dialogView.findViewById(R.id.dialog_loan_amount_btn_yes);
//        TextView inst_text = dialogView.findViewById(R.id.inst_text);
//        final EditText amount_text = dialogView.findViewById(R.id.dialog_loan_amount_amount);
//
//        inst_text.setText("After analysing the information availlable to us, we are pleased to tell you that you are qualified to a loan, not more than '"  + scoreResult.getAmount() + "'");
//        final AlertDialog alertDialog = builder.create();
//
//        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        alertDialog.setCancelable(true);
//        alertDialog.setCanceledOnTouchOutside(true);
//
//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//                String amount = amount_text.getText().toString();
//                if (!amount.equals("")){
//                    scoreReadyResult.setAmount(Integer.valueOf(amount));
//                }
//
//                // Fourteenth Method Call
//                getToken("offer");
//            }
//        });
//
//        dismissButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.show();
//    }



    private AppUser retrieveUser() {

        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")) {

            return null;
        }

        return gson.fromJson(json, AppUser.class);
    }

    private void saveCustomerInfo(CustomerModelResult customerModelResult) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_profile", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(customerModelResult);
        prefsEditor.putString("current_customer", json);
        prefsEditor.commit();
    }

    private CustomerModelResult retrieveCustomer() {
        SharedPreferences preferences = getContext().getSharedPreferences("current_customer_profile", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("current_customer", "");
        if (json.equals("")) {

            return null;
        }

        return gson.fromJson(json, CustomerModelResult.class);
    }


    private RunningServiceClass retrieveRunningServices(){
        SharedPreferences preferences = getContext().getSharedPreferences("all_running_services", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("running_service", "");
        if (json.equals("")) {

            return null;
        }

        return gson.fromJson(json, RunningServiceClass.class);
    }

    private void saveCustomerModel(CustomerModel customerModel) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_details", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(customerModel);
        prefsEditor.putString("customer_details", json);
        prefsEditor.commit();
    }

    private void saveLoanModel(LoanModelResult loanModelResult) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_loan", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loanModelResult);
        prefsEditor.putString("customer_loan", json);
        prefsEditor.commit();
    }


    private void saveLoanOffer(LoanOfferResult model) {

        SharedPreferences preferences = getActivity().getSharedPreferences("current_customer_loan_offer", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString("customer_loan_offer", json);
        prefsEditor.commit();
    }

    private void saveUserInfo(AppUser appUser) {
        SharedPreferences preferences = getActivity().getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appUser);
        prefsEditor.putString("app_user", json);
        prefsEditor.commit();
    }

    private void slideUpDown(LinearLayout layout) {

        if (!isPanelShown(layout)) {

            // Show the panel

            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),

                    R.anim.bottom_up);



            layout.startAnimation(bottomUp);

            layout.setVisibility(View.VISIBLE);



        }

        else {

            // Hide the Panel

            Animation bottomDown = AnimationUtils.loadAnimation(getContext(),

                    R.anim.bottom_down);



            layout.startAnimation(bottomDown);

            layout.setVisibility(View.GONE);

        }

    }



    private boolean isPanelShown(LinearLayout layout) {

        return layout.getVisibility() == View.VISIBLE;

    }

    public void changeFragment(String originatingFragment){
        Class fragmentClass = null;
        switch (originatingFragment) {
            case "show_privacy_view":
                fragmentClass = DialogForPrivacyFragment.class;
                break;
            case "show_created_user":
                fragmentClass = DisplayCreatedUserFragment.class;
                break;
            case "before_credit_score":
                fragmentClass = BeforeCreditScoreFragment.class;
                break;
            case "apply_for_loan":
                fragmentClass = RequestForLoanFragment.class;
                break;
            case "before_priority_data":
                fragmentClass = BeforePriorityDataFragment.class;
                break;
            case "score_ready":
                fragmentClass = ShowLoanAmountFragment.class;
                break;
        }

        setupFragmentToDisplay(fragmentClass);
    }
}
