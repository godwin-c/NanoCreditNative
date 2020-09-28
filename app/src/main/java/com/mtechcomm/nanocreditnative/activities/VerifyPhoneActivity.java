package com.mtechcomm.nanocreditnative.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.classes.AppUser;
import com.vdx.designertoast.DesignerToast;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    //It is the verification id that will be sent to the user
    private String mVerificationId;

    //The edittext to input the code
    private EditText editTextCode;

    //firebase auth object
    private FirebaseAuth mAuth;
    String mobile, source;
    Button btn_sms_code_verify_code, btn_sms_code_req_new_code;

    private static final String TAG = VerifyPhoneActivity.class.getSimpleName();
    private static final int MY_REQUEST_CODE = 1201;

    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);

        //and sending the verification code to the number
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        source = intent.getStringExtra("source");

        btn_sms_code_verify_code = findViewById(R.id.btn_sms_code_verify_code);
        btn_sms_code_req_new_code = findViewById(R.id.btn_sms_code_req_new_code);

        btn_sms_code_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });

        btn_sms_code_req_new_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(mobile);
            }
        });

        appUser = retrieveUser();
        sendVerificationCode(mobile);
    }

    //the method is sending verification code
    //the country id is concatenated "+234" + mobile,
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);

        }catch (Exception e){
            DesignerToast.Error(this,"Verification Code is Wrong", Gravity.CENTER,Toast.LENGTH_SHORT);

        }

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DesignerToast.Success(VerifyPhoneActivity.this,"Phone number Verified!!!",Gravity.CENTER,Toast.LENGTH_SHORT);

                            if (source.equals("register")){
//                                AppUser appUser = retrieveUser();
//
//                                appUser.setPhoneVerified(true);
//
//                                saveUserInfo(appUser);
                                phoneVerified(true);

                                Intent intent = new Intent();
                                //intent.putExtra("fragmentName", "RegDetailsFragment");
                                setResult(MY_REQUEST_CODE, intent);
                                finish();

//                                ((RegisterActivity) getApplicationContext()).onNextFormClicked("RegDetailsFragment");
//                                finish();

                            }else if(source.equals("login")){
                                //Toast.makeText(VerifyPhoneActivity.this,appUser.getSurname() + ", you are welcome",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyPhoneActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            }


                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Exit", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (source.equals("register")){

                                        phoneVerified(false);
//                                        AppUser appUser = retrieveUser();
//                                        if (appUser != null){
//                                            appUser.setPhoneVerified(true);
//                                            saveUserInfo(appUser)
//                                        }
                                       // appUser.setPhoneVerified(false);

//                                        saveUserInfo(appUser);

//                                        ((RegisterActivity) getApplicationContext()).onNextFormClicked("RegDetailsFragment");
//                                        finish();

                                        Intent intent = new Intent();
                                        //intent.putExtra("fragmentName", "RegDetailsFragment");
                                        setResult(MY_REQUEST_CODE, intent);
                                        finish();

                                    }else {
                                        finish();
                                    }
                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private void saveUserInfo(AppUser appUser) {
        SharedPreferences preferences = getSharedPreferences("current_app_user", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appUser);
        prefsEditor.putString("app_user", json);
        prefsEditor.commit();
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

    private void phoneVerified(boolean verified) {
        SharedPreferences preferences = getSharedPreferences("phone_verification", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("verified_number", verified);
        editor.apply();

    }
}
