package com.mtechcomm.nanocreditnative.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.activities.RegisterActivity;
import com.mtechcomm.nanocreditnative.adapters.PhoneCodeSpinnerAdapter;
import com.mtechcomm.nanocreditnative.classes.PhoneNumberChoice;
import com.mtechcomm.nanocreditnative.classes.PhoneNumberClass;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class PhoneNumberSelectFragment extends Fragment {

    public AppCompatSpinner spinner;
    ArrayAdapter adapter;
    View view;
    private TextInputLayout phonenumber_layout;
    private EditText phonenumber_edt;
    private TextView country_name;

    private static final String TAG = PhoneNumberSelectFragment.class.getSimpleName();

    public PhoneNumberSelectFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_number_select, container, false);

        ButterKnife.bind(this,view);

        spinner = view.findViewById(R.id.phone_code_spinner);
        phonenumber_layout = view.findViewById(R.id.input_layout_register_phonenumber);
        phonenumber_edt = view.findViewById(R.id.register_phonenumber);
        country_name = view.findViewById(R.id.country_name);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhoneNumberChoice phoneNumberChoice = new PhoneNumberChoice(getContext(),TAG);
        ArrayList<PhoneNumberClass> phoneNumberClasses = phoneNumberChoice.getCountryAndCodes();
        adapter  = new PhoneCodeSpinnerAdapter(getContext(),android.R.layout.simple_spinner_item,phoneNumberClasses){
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0){
//                    return false;
//                }else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView)view1;

//                if (position == 0){
//                    textView.setTextColor(Color.GRAY);
//                }else {
//                    textView.setTextColor(Color.BLACK);
//                }

                return view1;
            }
        };

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0){
//                    PhoneNumberClass phoneNumberClass = (PhoneNumberClass) adapter.getItem(position);
//
//                    country_name.setText(phoneNumberClass.getCountry());
//                    ((RegisterActivity) getActivity()).setSelectedPhoneCode(phoneNumberClass.getCode());
//                }

                PhoneNumberClass phoneNumberClass = (PhoneNumberClass) adapter.getItem(position);

                country_name.setText(phoneNumberClass.getCountry());
                ((RegisterActivity) getActivity()).setSelectedPhoneCode(phoneNumberClass.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}