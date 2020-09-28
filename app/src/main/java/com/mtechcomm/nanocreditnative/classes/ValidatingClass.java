package com.mtechcomm.nanocreditnative.classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class ValidatingClass implements TextWatcher {
    private EditText editText;
    private String erroMessage;

    private View view;

    public ValidatingClass(EditText edt_to_validate, String errMsg) {
        this.editText = edt_to_validate;
        this.erroMessage = errMsg;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!validateField(this.editText)){
            editText.setError(this.erroMessage);
        }
    }

    private boolean validateField(EditText edt) {
        if (edt.getText().toString().trim().isEmpty()) {

            return false;
        }
        return true;
    }
}
