package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputLayout emailLout,passwordLout;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLout = findViewById(R.id.login_email);
        passwordLout = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            if(validateUserDetails())
                Toast.makeText(getApplicationContext(),"Successful Login",Toast.LENGTH_SHORT).show();
        });
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

    public boolean validateUserDetails(){
        return !(!validateEmail() | !validatePassword());
    }

    private boolean validatePassword() {
        String val = passwordLout.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            passwordLout.setError("Field can't be empty");
            return false;
        }
        else{
            passwordLout.setError(null);
            passwordLout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailLout.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            emailLout.setError("Field can't be empty");
            return false;
        }
        else{
            emailLout.setError(null);
            emailLout.setErrorEnabled(false);
            return true;
        }
    }

    public void callForgetPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);
        startActivity(intent);
    }
}