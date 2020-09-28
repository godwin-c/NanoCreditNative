package com.mtechcomm.nanocreditnative.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.ValidatingClass;


public class RegDetailsFragment extends Fragment {

    View view;
    private TextInputLayout firstname_layout, lastname_layout, password_layout, conf_password, email_layout, date_of_birth_layout;
    private EditText firstname_edt, lastname_edt, password_edt, conf_password_edt, email_edt, date_of_birth_edt;

    public RegDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_details, container, false);

        firstname_edt = (EditText)view.findViewById(R.id.register_firstname);
        firstname_edt.addTextChangedListener(new ValidatingClass(firstname_edt, getString(R.string.err_msg_fname)));

        lastname_edt = (EditText)view.findViewById(R.id.register_lastname);
        lastname_edt.addTextChangedListener(new ValidatingClass(lastname_edt, getString(R.string.err_msg_lname)));

        password_edt = (EditText)view.findViewById(R.id.register_password);
        password_edt.addTextChangedListener(new ValidatingClass(password_edt, getString(R.string.err_msg_secret)));
//
        conf_password_edt = (EditText)view.findViewById(R.id.register_confirm_password);
        conf_password_edt.addTextChangedListener(new ValidatingClass(conf_password_edt, getString(R.string.err_msg_secret)));

        email_edt = (EditText)view.findViewById(R.id.register_email);

        date_of_birth_edt = (EditText)view.findViewById(R.id.register_date_of_birth);
//        date_of_birth_layout.addTextChangedListener(new ValidatingClass(username_edt, getString(R.string.err_msg_username)));


        firstname_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_firstname);
        lastname_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_lastname);

        password_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_password);
        conf_password = (TextInputLayout)view.findViewById(R.id.input_layout_register_confirm_password);
        email_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_email);
        date_of_birth_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_date_of_birth);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date_of_birth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateOfBirth();
            }
        });
    }

    public void getDateOfBirth() {
        // Get Current Date

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_time_dialog_layout,null);
        builder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.dob_datepicker);
        Button set_date_btn = dialogView.findViewById(R.id.dob_select_date_btn);

        final AlertDialog alertDialog = builder.create();
        set_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                date_of_birth_edt.setText(datePicker.getDayOfMonth() + " - " + (datePicker.getMonth() + 1) + " - " + datePicker.getYear());

            }
        });

        alertDialog.show();
    }
}