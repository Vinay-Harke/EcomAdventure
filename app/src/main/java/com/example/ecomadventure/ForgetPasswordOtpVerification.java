package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class ForgetPasswordOtpVerification extends AppCompatActivity {
    PinView pinFromUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_otp_verification);
        pinFromUser = findViewById(R.id.forget_pwd_pin_view);
    }

    public void callSetNewPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SetNewPasswordScreen.class);
        startActivity(intent);
    }

    public void callForgetPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

}