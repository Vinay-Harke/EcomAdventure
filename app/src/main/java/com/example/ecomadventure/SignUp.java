package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TextInputLayout nameLout, emailLout, addressLout, passwordLout;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameLout = findViewById(R.id.signUp_full_name);
        emailLout = findViewById(R.id.signUp_email);
        addressLout = findViewById(R.id.address);
        passwordLout = findViewById(R.id.signUp_password);
        signUpBtn = findViewById(R.id.signUp_screen_signUp_Btn);
        signUpBtn.setOnClickListener(view -> {
            if(validateUserDetails()){
                System.out.println("\n\n\n\n Hello Here it comes");
                Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateUserDetails() {
        return !(!validateEmail() | !validatePassword() | !validateAddress() | !validateName());
    }

    private boolean validateAddress() {
        String val = addressLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            addressLout.setError("Field can not be empty!");
            return false;
        } else {
            addressLout.setError(null);
            addressLout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordLout.getEditText().getText().toString().trim();
        Pattern password_pattern = Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$");
        if (val.isEmpty()) {
            passwordLout.setError("Field can not be empty");
            return false;
        } else if (!password_pattern.matcher(val).matches()) {
            passwordLout.setError("Password is too weak!");
            return false;
        } else {
            passwordLout.setError(null);
            passwordLout.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {
        String val = emailLout.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            emailLout.setError("Field can not be empty!");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailLout.setError("Invalid Email!");
            return false;
        } else {
            emailLout.setError(null);
            emailLout.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateName() {
        String val = nameLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            nameLout.setError("Field can not be empty!");
            return false;
        } else {
            nameLout.setError(null);
            nameLout.setErrorEnabled(false);
            return true;
        }

    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }
}