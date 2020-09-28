package com.mtechcomm.nanocreditnative.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mtechcomm.nanocreditnative.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentSchedulleFragment extends Fragment {


    public PaymentSchedulleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_schedulle, container, false);
    }

}
