package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class SetNewPasswordScreen extends AppCompatActivity {
    TextInputLayout newPasswordLout, confirmPasswordLout;
    Button submitBtn;
    DatabaseReference databaseReference;
    String newPassword;
    String confirmPassword;
    String username;
    DaoUser daoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password_screen);
        newPasswordLout = findViewById(R.id.reset_new_password);
        confirmPasswordLout = findViewById(R.id.reset_confirm_password);
        daoUser = new DaoUser();
        username = getIntent().getStringExtra("username");
        submitBtn = findViewById(R.id.new_cred_submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword = newPasswordLout.getEditText().getText().toString().trim();
                updatePasswordInUserDb(username,newPassword);
                Intent intent= new Intent(getApplicationContext(),PasswordUpdatedMsgScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updatePasswordInUserDb(String username, String newPassword) {
        databaseReference=daoUser.getRefToCheckIfUserExists(username);
        databaseReference.child("password").setValue(newPassword);
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