package com.mtechcomm.nanocreditnative.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.models.LoanEnquiryModelResult;

import java.util.Objects;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSummaryFragment extends Fragment {


    PieView pieView;
    TextView loan_summary_customer_name, loan_summary_loan_principal_amount,
            loan_summary_loan_outstanding_amount, loan_summary_loan_status,
            loan_summary_start_date, loan_summary_loan_end_date;

    LoanEnquiryModelResult result;

    public LoanSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_summary, container, false);

        loan_summary_customer_name = view.findViewById(R.id.loan_summary_customer_name);
        loan_summary_loan_principal_amount = view.findViewById(R.id.loan_summary_loan_principal_amount);
        loan_summary_loan_outstanding_amount = view.findViewById(R.id.loan_summary_loan_outstanding_amount);
        loan_summary_loan_status = view.findViewById(R.id.loan_summary_loan_status);
        loan_summary_loan_end_date = view.findViewById(R.id.loan_summary_loan_end_date);
        loan_summary_start_date = view.findViewById(R.id.loan_summary_start_date);


        pieView = (PieView) view.findViewById(R.id.pieView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        result = retrieveLoanInfo();

        assert result != null;

        AppUser appUser = retrieveUser();

        assert appUser != null;


        loan_summary_customer_name.setText(String.format("%s %s", appUser.getSurname(), appUser.getOthernames()));
        loan_summary_loan_principal_amount.setText(String.valueOf(result.getAmount()));
        loan_summary_loan_outstanding_amount.setText(String.valueOf(result.getOutstanding()));
        loan_summary_loan_status.setText(result.getStatus());
        loan_summary_loan_end_date.setText(result.getEndDate());
        loan_summary_start_date.setText(result.getStartDate());

        float repaymentPercentage = getPercentage((Integer.valueOf(result.getAmount())),Integer.valueOf(result.getOutstanding()));

        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.black));
       // pieView.setInnerText("75%");
        pieView.setPercentage(repaymentPercentage);
        PieAngleAnimation animation = new PieAngleAnimation(pieView);
        animation.setDuration(5000); //This is the duration of the animation in millis
        pieView.startAnimation(animation);
    }

    private float getPercentage(int loanAmount, float outStandingAmount){
        return (float) ((outStandingAmount / loanAmount) * 100);
    }

    private LoanEnquiryModelResult retrieveLoanInfo(){

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("current_customer_loan_offer", Context.MODE_PRIVATE);

        Gson gson;
        gson = new Gson();
        String json = preferences.getString("customer_loan", "");
        if (json.equals("")){
            return  null;
        }

        return gson.fromJson(json, LoanEnquiryModelResult.class);
    }

    private AppUser retrieveUser(){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")){
            return  null;
        }

        return gson.fromJson(json, AppUser.class);
    }
}
