package com.mtechcomm.nanocreditnative.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerServiceFragment extends Fragment {

    TextInputLayout input_layout_cu_service_msg_topic, input__layout_cu_service_msg_email, input_layout_layout_cu_service_msg_body;
    EditText edt__layout_cu_service_msg_email, edt__layout_cu_service_msg_body, edt_layout_cu_service_msg_topic;
    Button btn_cus_service_submit_msg;

    public CustomerServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_service, container, false);

        input_layout_cu_service_msg_topic = view.findViewById(R.id.input_layout_cu_service_msg_topic);
        input__layout_cu_service_msg_email = view.findViewById(R.id.input__layout_cu_service_msg_email);
        input_layout_layout_cu_service_msg_body = view.findViewById(R.id.input_layout_layout_cu_service_msg_body);

        edt__layout_cu_service_msg_email = view.findViewById(R.id.edt__layout_cu_service_msg_email);
        edt__layout_cu_service_msg_body = view.findViewById(R.id.edt_layout_cu_service_msg_body);
        edt_layout_cu_service_msg_topic = view.findViewById(R.id.edt_layout_cu_service_msg_topic);

        btn_cus_service_submit_msg = view.findViewById(R.id.btn_cus_service_submit_msg);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppUser appUser = retrieveUser();

        edt__layout_cu_service_msg_email.setText(appUser.getEmailAddress());

        btn_cus_service_submit_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        if (!validateField(edt_layout_cu_service_msg_topic, input_layout_cu_service_msg_topic, "tell us the subject")) {
            return;
        }

        if (!validateField(edt__layout_cu_service_msg_body, input_layout_layout_cu_service_msg_body, "what message do you want to send")) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
    }

    private boolean validateField(EditText editText, TextInputLayout layout_to_focus, String error_mesg) {
        if (editText.getText().toString().trim().isEmpty()) {
            layout_to_focus.setError(error_mesg);
            requestFocus(editText);

            return false;

        } else {
            layout_to_focus.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private boolean validateEmail() {
        String email = edt__layout_cu_service_msg_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input__layout_cu_service_msg_email.setError(getString(R.string.err_msg_email));
            requestFocus(edt__layout_cu_service_msg_email);
            return false;
        } else {
            input__layout_cu_service_msg_email.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
