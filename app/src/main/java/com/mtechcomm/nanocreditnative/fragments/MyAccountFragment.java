package com.mtechcomm.nanocreditnative.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.braintreepayments.cardform.view.CardForm;
import com.google.zxing.client.android.CaptureActivity;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.activities.LoginActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    AppCompatActivity activity;
    Button scan_id_btn;
    TextView scan_result_view;
    private static final String TAG = MyAccountFragment.class.getSimpleName();

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        final CardForm cardForm = view.findViewById(R.id.card_form);
        scan_id_btn = view.findViewById(R.id.my_account_btn_scan_id);
        scan_result_view = view.findViewById(R.id.my_account_id_card_number_txt);

        scan_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SAVE_HISTORY", false);
                startActivityForResult(intent, 0);
            }
        });

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(activity);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        cardForm.getCountryCodeEditText().setVisibility(View.GONE);
        cardForm.getPostalCodeEditText().setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (logoutRequested()){
//
//            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText("Logout?")
//                    .setContentText("You have chosen to logout from NanoCredit!")
//                    .setConfirmText("Logout!")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            requestLogout(false);
//                            sDialog.dismissWithAnimation();
//                        }
//                    })
//                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            cancelLogout();
//                            sDialog.dismissWithAnimation();
//                        }
//                    })
//                    .show();
//        }
    }

    private boolean logoutRequested() {
        SharedPreferences preferences = getActivity().getSharedPreferences("logout_requested", Context.MODE_PRIVATE);

        boolean response = preferences.getBoolean("logout_request", false);

        return response;
    }

    private void requestLogout(boolean b) {
        SharedPreferences preferences = getActivity().getSharedPreferences("logout_requested", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();

        prefsEditor.putBoolean("logout_request", b);
        prefsEditor.commit();

        performLogout();
    }

    private void performLogout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void cancelLogout(){
        SharedPreferences preferences = getActivity().getSharedPreferences("logout_requested", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();

        prefsEditor.putBoolean("logout_request", false);
        prefsEditor.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "contents: " + contents);
                scan_result_view.setText(contents);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "RESULT_CANCELED");
            }
        }
    }
}
