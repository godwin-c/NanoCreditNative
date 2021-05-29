package com.mtechcomm.nanocreditnative.fragments.forLoans;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.lenddo.mobile.core.LenddoCoreInfo;
import com.lenddo.mobile.core.LenddoCoreUtils;
import com.lenddo.mobile.datasdk.AndroidData;
import com.lenddo.mobile.datasdk.listeners.OnDataSendingCompleteCallback;
import com.lenddo.mobile.datasdk.models.ClientOptions;
import com.mtechcomm.nanocreditnative.MainActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.NetworkAvaillabilityClass;
import com.mtechcomm.nanocreditnative.fragments.ApplyForLoanFragment;
import com.mtechcomm.nanocreditnative.models.LoanModelResult;
import com.vdx.designertoast.DesignerToast;


public class BeforeCreditScoreFragment extends Fragment {

    Button before_credit_score_proceed_to_credit_score_btn_no, before_credit_score_proceed_to_credit_score_btn_yes;

    private ProgressDialog mProgressDialog;

    private static final String TAG = BeforeCreditScoreFragment.class.getSimpleName();
    public BeforeCreditScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_credit_score, container,false);
        before_credit_score_proceed_to_credit_score_btn_no = view.findViewById(R.id.before_credit_score_proceed_to_credit_score_btn_no);
        before_credit_score_proceed_to_credit_score_btn_yes = view.findViewById(R.id.before_credit_score_proceed_to_credit_score_btn_yes);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        before_credit_score_proceed_to_credit_score_btn_no.setOnClickListener(new View.OnClickListener() {
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
                                ((MainActivity) getActivity()).finish();
                            }
                        }).show();

            }
        });

        before_credit_score_proceed_to_credit_score_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(getContext());
                if (networkAvaillabilityClass.hasNetwork()) {
                    before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(false);
                    double androidVersion = currentVersion();
                    Log.d(TAG, "onClick: Android Version : " + androidVersion);

//                    DesignerToast.Error(getContext(),"Unfortunately, your Android Version '" + androidVersion + "' is not yet supported",Gravity.CENTER,Toast.LENGTH_SHORT);

                    setupLenddo();

//                    if (androidVersion >= 10.0){
//
//                        DesignerToast.Error(getContext(),"Unfortunately, your Android Version '" + androidVersion + "' is not yet supported for Credit.",Gravity.CENTER,Toast.LENGTH_SHORT);
//                        before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
//                    }else{
//                        setupLenddo();
//                    }

                }else {
                    DesignerToast.Error(getContext(),"not connected to the internet",Gravity.CENTER,Toast.LENGTH_SHORT);
                }

            }
        });
    }

    // Eight Method Call
    private void setupLenddo() {

        LoanModelResult loanModelResult = getCustomerLoanDetails();

        AndroidData.clear(getContext());

        //partnerScriptID = getContext().getString(R.string.partner_script_id);
        LenddoCoreInfo.setDataPartnerScriptId(getContext().getString(R.string.partner_script_id));

        LenddoCoreUtils.checkGooglePlayServices(getActivity());
        ClientOptions clientOptions = new ClientOptions();
        clientOptions.registerDataSendingCompletionCallback(new OnDataSendingCompleteCallback() {
            @Override
            public void onDataSendingSuccess() {
                //before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
                // call your routines here (response status code is 20x ~ 30x)
                Log.d("Callback", "Data sending completed successfully!");
                //showCustomerInfo();


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                            ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }

                        DesignerToast.Success(getContext(), "information gathering, success !!!", Gravity.CENTER, Toast.LENGTH_SHORT);
                        before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
//                        slideUpDown(proceed_to_credit_score);
//                        slideUpDown(proceed_to_show_loan_app_info);
//                        showCustomerInfo();

                        callMainFragment();
                    }
                });
            }

            @Override
            public void onDataSendingError(int statusCode, String errorMessage) {
                // call your routines here
                Log.d("Lenddo Callback", "Data sending failed! statuscode: " + statusCode + " error:" + errorMessage);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                            ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
                        DesignerToast.Error(getContext(), "information gathering failed!, with Error Message '" + errorMessage + "'", Gravity.CENTER, Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onDataSendingFailed(Throwable t) {

                // call your routines here
                Log.e("Lenddo Callback", "Network connection failure! " + t.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                            ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }

                        DesignerToast.Warning(getContext(), "information gathering has failed, try again", Gravity.CENTER, Toast.LENGTH_SHORT);
                        before_credit_score_proceed_to_credit_score_btn_yes.setEnabled(true);
                    }
                });
            }

            @Override
            public void onDataSendingStart() {
                //DesignerToast.Info(getContext(), "Data sending has begun", Gravity.CENTER, Toast.LENGTH_SHORT);
                Log.i(TAG, "onDataSendingStart: Data sending has started");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // DesignerToast.Info(getContext(), "Data sending has begun", Gravity.CENTER, Toast.LENGTH_SHORT);

                        mProgressDialog = new ProgressDialog(getContext());
                        ((Activity) getContext()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("gathering information for credit score ...");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();

                    }
                });
            }

        });

        AndroidData.setup(getContext(), clientOptions);

        Log.d(TAG, "setupLenddo: AppID in USE : " + loanModelResult.getApplicationId());

        LenddoCoreUtils.setApplicationId(getContext(),String.valueOf(loanModelResult.getApplicationId()));
        Log.d(TAG, "LenddoCore : AppID in USE : " + LenddoCoreUtils.getApplicationId(getContext()));

        AndroidData.startAndroidData(getActivity(), String.valueOf(loanModelResult.getApplicationId()));

    }

    private void callMainFragment(){
        Fragment parent = getParentFragment();
        if(parent instanceof ApplyForLoanFragment) {
            ((ApplyForLoanFragment) parent).changeFragment("before_priority_data");
        }

    }

    private LoanModelResult getCustomerLoanDetails(){
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.sharepref_files), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(getString(R.string.customer_loan_details_save), "");
        if (json.equals("")){
            return  null;
        }
        LoanModelResult loanModelResult = gson.fromJson(json, LoanModelResult.class);

        return loanModelResult;
    }

    //Current Android version data
    public static double currentVersion(){
        double release=Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)","$1"));
//        String codeName="Unsupported";//below Jelly bean OR above Oreo
//        if(release>=4.1 && release<4.4)codeName="Jelly Bean";
//        else if(release<5)codeName="Kit Kat";
//        else if(release<6)codeName="Lollipop";
//        else if(release<7)codeName="Marshmallow";
//        else if(release<8)codeName="Nougat";
//        else if(release<9)codeName="Oreo";
//        return codeName+" v"+release+", API Level: "+Build.VERSION.SDK_INT;
        return release;
    }
}