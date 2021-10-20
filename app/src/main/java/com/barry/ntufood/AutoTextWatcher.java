package com.barry.ntufood;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.barry.ntufood.ValidationHelper.*;

public class AutoTextWatcher implements TextWatcher {
    private TextInputLayout currControl;
    private Context context;

    AutoTextWatcher(TextInputLayout control, Context context) {
        this.currControl = control;
        this.context = context;
    }
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String currentText = currControl.getEditText().getText().toString();
        if(isNullOrEmpty(currentText)){
            currControl.setErrorEnabled(false);
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        String currentText = currControl.getEditText().getText().toString();

        switch (currControl.getEditText().getInputType()) {
            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                if(isValidEmail(currentText) || isNullOrEmpty(currentText) ){
                    currControl.setErrorEnabled(false);
                } else {
                    currControl.setError(context.getString(R.string.email_error));
                };
                break;
            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                if(isValidPassword(currentText) || isNullOrEmpty(currentText)){
                    currControl.setErrorEnabled(false);
                } else {
                    currControl.setError(context.getString(R.string.password_error));
                };
                break;

        }
    }
}