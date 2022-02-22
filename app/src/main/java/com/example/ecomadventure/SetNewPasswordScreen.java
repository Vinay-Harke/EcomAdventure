package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SetNewPasswordScreen extends AppCompatActivity {
    TextInputLayout newPasswordLout, confirmPasswordLout;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password_screen);
        newPasswordLout = findViewById(R.id.reset_new_password);
        confirmPasswordLout = findViewById(R.id.reset_confirm_password);
        loginBtn = findViewById(R.id.reset_password_success_login_btn);
    }

    public void callPasswordUpdatedMsgScreen(View view) {
        if (validateEnteredData()) {
            Intent intent = new Intent(getApplicationContext(), PasswordUpdatedMsgScreen.class);
            startActivity(intent);
        }
    }

    private boolean validateEnteredData() {
        return !(!validateNewPassword() | !validateNewPasswordConfirmPasswordIsSame());
    }

    private boolean validateNewPasswordConfirmPasswordIsSame() {
        String val1 = newPasswordLout.getEditText().getText().toString().trim();
        String val2 = confirmPasswordLout.getEditText().getText().toString().trim();
        if (val1.equals(val2))
            return true;
        Toast.makeText(getApplicationContext(), "New Password and Confirm Password Text Does Not Match !", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean validateNewPassword() {
        String val = newPasswordLout.getEditText().getText().toString().trim();
        Pattern password_pattern = Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$");
        if (val.isEmpty()) {
            newPasswordLout.setError("Field can't be empty");
            return false;
        } else if (!password_pattern.matcher(val).matches()) {
            newPasswordLout.setError("Password is too weak!");
            return false;
        } else {
            newPasswordLout.setError(null);
            newPasswordLout.setErrorEnabled(false);
            return true;
        }
    }
}