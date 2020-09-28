package com.mtechcomm.nanocreditnative.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.mtechcomm.nanocreditnative.classes.CustomCallBack;
import com.mtechcomm.nanocreditnative.classes.ValidatingClass;
import com.mtechcomm.nanocreditnative.fragments.PhoneNumberSelectFragment;
import com.mtechcomm.nanocreditnative.fragments.RegDetailsFragment;
import com.mtechcomm.nanocreditnative.models.CheckEmailModel;
import com.mtechcomm.nanocreditnative.models.CheckMSISDNmodel;
import com.mtechcomm.nanocreditnative.models.CheckMSISDNresult;
import com.mtechcomm.nanocreditnative.models.MyErrorClass;
import com.mtechcomm.nanocreditnative.models.RegisterDetailsModel;
import com.mtechcomm.nanocreditnative.models.RegisterResponseModel;
import com.mtechcomm.nanocreditnative.net.Mtech_API_Interface;
import com.mtechcomm.nanocreditnative.net.Mtech_API_client;
import com.vdx.designertoast.DesignerToast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1201;

    private TextInputLayout firstname_layout, lastname_layout, phonenumber_layout, password_layout, conf_password, email_layout, date_of_birth_layout;
    private EditText firstname_edt, lastname_edt, phonenumber_edt, password_edt, conf_password_edt, email_edt, date_of_birth_edt;
    Button register_btn, next_form, prev_form;
    private int mYear, mMonth, mDay;
    AppUser appUser;

    Class fragmentClass;

    String selectedPhoneCode = null;
    String chosenPhoneNumber = "";

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        next_form = findViewById(R.id.btn_register_next_screen);
        prev_form = findViewById(R.id.btn_register_back_screen);


//        register_btn = (Button)findViewById(R.id.register_btn);
//
//        register_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitForm();
//            }
//        });


        fragmentClass = PhoneNumberSelectFragment.class;
        setupFragmentToDisplay(fragmentClass);

        next_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentClass.getSimpleName().equals("PhoneNumberSelectFragment")) {
                    if (selectedPhoneCode == null) {
                        Toast.makeText(RegisterActivity.this, "select you country code", Toast.LENGTH_SHORT).show();
                    } else {

                        onNextFormClicked("PhoneNumberSelectFragment");
                    }
                } else if (fragmentClass.getSimpleName().equals("RegDetailsFragment")) {

                    onNextFormClicked("RegDetailsFragment");


                }

            }
        });

        prev_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentClass.getSimpleName().equals("RegDetailsFragment")) {

                    fragmentClass = PhoneNumberSelectFragment.class;
                    setupFragmentToDisplay(fragmentClass);

                    selectedPhoneCode = null;
                    prev_form.setVisibility(View.GONE);
                    next_form.setText("Next");
                }
            }
        });
    }

    public void onNextFormClicked(String phoneNumberSelectFragment) {

        if (phoneNumberSelectFragment.equals("PhoneNumberSelectFragment")) {

            FragmentManager fm = getSupportFragmentManager();

            PhoneNumberSelectFragment regDetailsFragment = (PhoneNumberSelectFragment) fm.findFragmentById(R.id.reg_user_framelayout);
            // String name = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.fullname_txt_input)).getText().toString();

            View view = regDetailsFragment.getView();

            phonenumber_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_phonenumber);
            phonenumber_edt = (EditText) view.findViewById(R.id.register_phonenumber);
            phonenumber_edt.addTextChangedListener(new ValidatingClass(phonenumber_edt, getString(R.string.err_msg_phone)));

            if (!validatePhoneNumber(phonenumber_edt, phonenumber_layout, getString(R.string.err_msg_phone))) {
                DesignerToast.Error(RegisterActivity.this,"Invalid phone number",Gravity.CENTER,Toast.LENGTH_SHORT);
                return;
            }

            chosenPhoneNumber = selectedPhoneCode + phonenumber_edt.getText().toString();

            checkPhoneNumber(chosenPhoneNumber);

        } else if (phoneNumberSelectFragment.equals("RegDetailsFragment")) {


            FragmentManager fm = getSupportFragmentManager();

            RegDetailsFragment regDetailsFragment = (RegDetailsFragment) fm.findFragmentById(R.id.reg_user_framelayout);
            // String name = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.fullname_txt_input)).getText().toString();


            View view = regDetailsFragment.getView();

            firstname_edt = (EditText) view.findViewById(R.id.register_firstname);
            firstname_edt.addTextChangedListener(new ValidatingClass(firstname_edt, getString(R.string.err_msg_fname)));

            lastname_edt = (EditText) view.findViewById(R.id.register_lastname);
            lastname_edt.addTextChangedListener(new ValidatingClass(lastname_edt, getString(R.string.err_msg_lname)));

            password_edt = (EditText) view.findViewById(R.id.register_password);
            password_edt.addTextChangedListener(new ValidatingClass(password_edt, getString(R.string.err_msg_secret)));
//
            conf_password_edt = (EditText) view.findViewById(R.id.register_confirm_password);
            conf_password_edt.addTextChangedListener(new ValidatingClass(conf_password_edt, getString(R.string.err_msg_secret)));

            email_edt = (EditText) view.findViewById(R.id.register_email);

            date_of_birth_edt = (EditText) view.findViewById(R.id.register_date_of_birth);
//        date_of_birth_layout.addTextChangedListener(new ValidatingClass(username_edt, getString(R.string.err_msg_username)));


            firstname_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_firstname);
            lastname_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_lastname);
//                    phonenumber_layout = (TextInputLayout)view.findViewById(R.id.input_layout_register_phonenumber);
            password_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_password);
            conf_password = (TextInputLayout) view.findViewById(R.id.input_layout_register_confirm_password);
            email_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_email);
            date_of_birth_layout = (TextInputLayout) view.findViewById(R.id.input_layout_register_date_of_birth);

//                    TextInputEditText nameTextInputEditText = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.fullname_txt_input));
//                    TextInputEditText passwordTextInputEditText = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.password_txt_input));
//                    TextInputEditText confTextInputEditText = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.confirm_password_txt_input));
//                    TextInputEditText emailTextInputEditText = ((TextInputEditText) regDetailsFragment.getView().findViewById(R.id.email_txt_input));
//
//                    TextInputLayout nameTextInputLayout = ((TextInputLayout) regDetailsFragment.getView().findViewById(R.id.fullname_txt_input_inp));
//                    TextInputLayout email_txt_input_inp = ((TextInputLayout) regDetailsFragment.getView().findViewById(R.id.email_txt_input_inp));
//                    TextInputLayout passwordTextInputLayout = ((TextInputLayout) regDetailsFragment.getView().findViewById(R.id.password_txt_input_inp));
//                    TextInputLayout confTextInputLayout = ((TextInputLayout) regDetailsFragment.getView().findViewById(R.id.confirm_password_txt_input_inp));

            // Button account_type_display_btn = ((Button) regDetailsFragment.getView().findViewById(R.id.account_type_display_btn));


            submitForm();
        }


    }

    private void setupFragmentToDisplay(Class fragmentClass) {
        Fragment fragment = null;
        try {

            fragment = (Fragment) fragmentClass.newInstance();


        } catch (Exception e) {

            e.printStackTrace();

        }

        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;


        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag((fragmentTag)) == null) {
            fragmentManager.beginTransaction().replace(R.id.reg_user_framelayout, fragment).addToBackStack(fragment.getTag()).commit();
        }
    }

    public void setSelectedPhoneCode(String phoneCode) {
        this.selectedPhoneCode = phoneCode;
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateField(firstname_edt, firstname_layout, getString(R.string.err_msg_fname))) {
            return;
        }

        if (!validateField(lastname_edt, lastname_layout, getString(R.string.err_msg_lname))) {
            return;
        }


        if (!validateField(password_edt, password_layout, getString(R.string.err_msg_secret))) {
            return;
        }
        if (!validateField(date_of_birth_edt, date_of_birth_layout, getString(R.string.err_msg_date_of_birth))) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword(conf_password_edt, password_edt, conf_password, getString(R.string.wrong_conf_pin))) {
            return;
        }

        ArrayList<String> phones = new ArrayList<>();
        phones.add(chosenPhoneNumber);

        appUser = new AppUser(firstname_edt.getText().toString(),lastname_edt.getText().toString(),phones,email_edt.getText().toString(),
                date_of_birth_edt.getText().toString(),password_edt.getText().toString(),"",phoneVerified(),
                0, "", 0, "false",""); //retrieveUser();

        saveUserInfo(appUser);

        checkIfEmailExists(appUser.getEmailAddress());
        //sendOTP(appUser.getPhones().get(0));
//        showInputDialog();
    }

    private void checkIfEmailExists(String emailAddress) {
        Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
        Call<CheckMSISDNresult> call = mtech_api_interface.checkEmail(new CheckEmailModel(emailAddress));
        call.enqueue(new CustomCallBack<>(RegisterActivity.this, new Callback<CheckMSISDNresult>() {
            @Override
            public void onResponse(Call<CheckMSISDNresult> call, Response<CheckMSISDNresult> response) {

                if (response.code() == 201) {

                    Log.d(TAG, "onResponse:  ret " + response.body().getSuccess());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                   // DesignerToast.Error(RegisterActivity.this, "email address '" + appUser.getEmailAddress() + "' is already in use.", Gravity.CENTER, Toast.LENGTH_SHORT);
                    continueWithRegister();

                } else if (response.code() == 400) {

                    Log.d(TAG, "onResponse:  ret " + response.body());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

//                    sendOTP(appUser.getPhones().get(0));
                    continueWithRegister();
                }

            }

            @Override
            public void onFailure(Call<CheckMSISDNresult> call, Throwable t) {

                Log.d(TAG, "onResponse:  ret " + t.getMessage());
                Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());

            }
        }));
    }

    private void continueWithRegister() {

        appUser = retrieveUser();
        String verificationStatus = "false";
        if (appUser.isPhoneVerified() == true)
            verificationStatus = "true";


        Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
        RegisterDetailsModel model = new RegisterDetailsModel(appUser.getSurname(), appUser.getOthernames(), appUser.getPhones().get(0), appUser.getEmailAddress(), appUser.getDate_of_birth(), appUser.getPassword(),verificationStatus , appUser.getSurname() + " " + appUser.getOthernames(), " ");
        Call<RegisterResponseModel> call = mtech_api_interface.registerUser(model); //loginUser(new LoginUserDetailsModel(username,password));

        call.enqueue(new CustomCallBack<>(RegisterActivity.this, new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                if (response.code() == 201) {
                    Log.d(TAG, "onResponse: " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.body());

                    RegisterResponseModel model = response.body();

//                    AppUser appUser = new AppUser(model.getFirstname(),model.getLastname(),phones,model.getEmail(),model.getDob(),model.getPassword(),model.getAccountnumber(),model.getNumberVerified()); //retrieveUser();
//                    saveUserInfo(appUser);

                    DesignerToast.Success(RegisterActivity.this, model.getSuccess(), Gravity.CENTER, Toast.LENGTH_SHORT);
                    //verification successful we will start the profile activity
                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();

                } else {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MyErrorClass>() {
                    }.getType();
                    MyErrorClass errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Log.d(TAG, "onResponse:  ret " + errorResponse.getError());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

                    DesignerToast.Error(RegisterActivity.this, errorResponse.getError(), Gravity.CENTER, Toast.LENGTH_SHORT);

                }

            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                DesignerToast.Error(RegisterActivity.this, t.getMessage(), Gravity.CENTER, Toast.LENGTH_SHORT);
                Log.d(TAG, "onResponse: " + getClass().getEnclosingMethod().getName());
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        }));


    }

    private void checkPhoneNumber(String phoneNumber) {
        Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
        Call<CheckMSISDNresult> call = mtech_api_interface.checkMSISDN(new CheckMSISDNmodel(phoneNumber));
        call.enqueue(new CustomCallBack<>(RegisterActivity.this, new Callback<CheckMSISDNresult>() {
            @Override
            public void onResponse(Call<CheckMSISDNresult> call, Response<CheckMSISDNresult> response) {

                if (response.code() == 201) {

                    Log.d(TAG, "onResponse:  ret " + response.body());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

//                    DesignerToast.Error(RegisterActivity.this, "phone number '" + phoneNumber + "' is already in use.", Gravity.CENTER, Toast.LENGTH_SHORT);

                    sendOTP(phoneNumber);

                } else if (response.code() == 400) {

                    Log.d(TAG, "onResponse:  ret " + response.body());
                    Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());
                    Log.d(TAG, "onResponse: " + response.code());

//                    fragmentClass = RegDetailsFragment.class;
//                    setupFragmentToDisplay(fragmentClass);
//
//                    prev_form.setVisibility(View.VISIBLE);
//                    next_form.setText("Sign Up");
                    sendOTP(phoneNumber);


                }
            }

            @Override
            public void onFailure(Call<CheckMSISDNresult> call, Throwable t) {

                Log.d(TAG, "onResponse:  ret " + t.getMessage());
                Log.d(TAG, "onResponse:  ret " + getClass().getEnclosingMethod().getName());

                DesignerToast.Error(RegisterActivity.this,t.getMessage(),Gravity.CENTER,Toast.LENGTH_SHORT);
            }
        }));
    }

    private void sendOTP(String phone) {

        if (phone.isEmpty() || phone.length() < 10) {
            phonenumber_edt.setError("Enter a valid mobile number");
            phonenumber_edt.requestFocus();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("mobile", phone);
        intent.putExtra("source", "register");
        intent.setClassName("com.mtechcomm.nanocreditnative","com.mtechcomm.nanocreditnative.activities.VerifyPhoneActivity");
        startActivityForResult(intent, MY_REQUEST_CODE);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == MY_REQUEST_CODE) {
            //String fragmentName = intent.getStringExtra("fragmentName");
            //onNextFormClicked(fragmentName);

            fragmentClass = RegDetailsFragment.class;
            setupFragmentToDisplay(fragmentClass);
            prev_form.setVisibility(View.VISIBLE);
            next_form.setText("Submit");
           // textView.setText(intent.getStringExtra(“text”));

        }else {
            DesignerToast.Error(RegisterActivity.this,"something went wrong", Gravity.CENTER,Toast.LENGTH_SHORT);
        }
    }

    private AppUser retrieveUser() {

        SharedPreferences preferences = getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("app_user", "");
        if (json.equals("")) {

            return null;
        }

        return gson.fromJson(json, AppUser.class);
    }

    private void saveUserInfo(AppUser appUser) {
        SharedPreferences preferences = getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appUser);
        prefsEditor.putString("app_user", json);
        prefsEditor.apply();
    }

    private boolean validatePhoneNumber(EditText editText, TextInputLayout layout_to_focus, String error_mesg){
        String txt_number = editText.getText().toString();

        if(txt_number.matches("^[0-9]*$") && txt_number.length() > 9){
            layout_to_focus.setErrorEnabled(false);
          return true;
        }else {
            layout_to_focus.setError(error_mesg);
            requestFocus(editText);
            return false;
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

    private boolean validatePassword(EditText confPasswordEDT, EditText passwordEDT, TextInputLayout layout_to_focus, String error_mesg) {
        if (!passwordEDT.getText().toString().equals(confPasswordEDT.getText().toString())) {
            layout_to_focus.setError(error_mesg);
            requestFocus(confPasswordEDT);

            return false;

        } else {
            layout_to_focus.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = email_edt.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            email_layout.setError(getString(R.string.err_msg_email));
            requestFocus(email_edt);
            return false;
        } else {
            email_layout.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(RegisterActivity.this);
        View promptView = layoutInflater.inflate(R.layout.sms_code_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        final Button re_new_code = (Button) promptView.findViewById(R.id.sms_code_req_new_code);
        final Button verify_code = (Button) promptView.findViewById(R.id.sms_code_verify_code);

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();


        re_new_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "A new Code has just been sent to you, Please Enter it here", Toast.LENGTH_LONG).show();
//               alert.dismiss();
            }
        });

        verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle( "Code Verified !!!" )
                        .setMessage("You are welcome to NanoCredit!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                                saveUserInfo(appUser);

                                finish();
                            }
                        }).show();

//                new SweetAlertDialog(RegisterActivity.this)
//                        .setTitleText("Code Verified !!!")
//                        .setContentText("You are welcome to NanoCredit!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                saveUserInfo(appUser);
//
//                                finish();
//                            }
//                        })
//                        .show();
            }
        });


        alert.show();
    }

    public void getDateOfBirth(View view) {
        // Get Current Date
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//
//                        date_of_birth_edt.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
//
//                    }
//                }, mYear, mMonth, mDay);
//
//
//        datePickerDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_time_dialog_layout, null);
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

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.reg_user_framelayout);
//        PhoneNumberSelectFragment
        if (fragment instanceof PhoneNumberSelectFragment) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private boolean phoneVerified() {
        SharedPreferences preferences = getSharedPreferences("phone_verification", Context.MODE_PRIVATE);

        return preferences.getBoolean("verified_number",false);

    }
}
