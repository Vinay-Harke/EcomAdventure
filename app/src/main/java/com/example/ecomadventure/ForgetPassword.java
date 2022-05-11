package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ForgetPassword extends AppCompatActivity {
    TextInputLayout emailLout;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailLout = findViewById(R.id.forget_password_email_id);
        submitBtn = findViewById(R.id.forget_pwd_submit_btn);
    }

    public void callForgetPasswordOTPVerification(View view) {
        if (validateUserData()) {
            Intent intent = new Intent(getApplicationContext(), ForgetPasswordOtpVerification.class);
            startActivity(intent);
        }

    }

    private boolean validateUserData() {
        String val = emailLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            emailLout.setError("Field can't be empty");
            return false;
        } else {
            emailLout.setError(null);
            emailLout.setErrorEnabled(false);
            return true;
        }
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}