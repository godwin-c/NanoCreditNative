package com.mtechcomm.nanocreditnative.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQsFragment extends Fragment {


    public FAQsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faqs, container, false);
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
}
