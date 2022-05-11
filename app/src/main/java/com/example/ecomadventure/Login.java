package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputLayout emailLout, passwordLout;
    MaterialCardView login_card_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLout = findViewById(R.id.login_email);
        passwordLout = findViewById(R.id.login_password);
        login_card_view = findViewById(R.id.login_card);
        emailLout.animate().alpha(1).setDuration(1600).translationX(20);
        passwordLout.animate().alpha(1).setDuration(1700).translationX(20);
        login_card_view.animate().alpha(1).setDuration(1500).translationY(100);
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public boolean validateUserDetails() {
        return !(!validateEmail() | !validatePassword());
    }

    private boolean validatePassword() {
        String val = passwordLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            passwordLout.setError("Field can't be empty");
            return false;
        } else {
            passwordLout.setError(null);
            passwordLout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
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

    public void callForgetPasswordScreen(View view) {
        //Intent intent = new Intent(getApplicationContext(), ViewDetails.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), PdfDownload.class);
        startActivity(intent);

    }

    public void callHomeScreen(View view) {
        if (validateUserDetails()) {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
        }
    }
}