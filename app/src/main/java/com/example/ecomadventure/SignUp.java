package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    MaterialCardView signUpCardLout;
    TextInputLayout nameLout, emailLout, phoneLout, passwordLout;
    Button signUpBtn;
    DatabaseReference databaseReference;
    DaoUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpCardLout = findViewById(R.id.signUp_card_lout);
        nameLout = findViewById(R.id.signUp_user_name);
        emailLout = findViewById(R.id.signUp_email);
        phoneLout = findViewById(R.id.signUp_phone);
        passwordLout = findViewById(R.id.signUp_password);
        signUpBtn = findViewById(R.id.signUp_screen_signUp_Btn);
        nameLout.animate().alpha(1).setDuration(1200).translationX(1);
        emailLout.animate().alpha(1).setDuration(1300).translationX(1);
        phoneLout.animate().alpha(1).setDuration(1400).translationX(1);
        passwordLout.animate().alpha(1).setDuration(1500).translationX(1);
        daoUser = new DaoUser();

        signUpBtn.setOnClickListener(view -> {
            if (validateUserDetails()) {

                String name = nameLout.getEditText().getText().toString().trim();
                String email = emailLout.getEditText().getText().toString().trim();
                String phone ="+91"+phoneLout.getEditText().getText().toString().trim();
                String password = passwordLout.getEditText().getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putString("Name", name);
                bundle.putString("Email", email);
                bundle.putString("Password", password);
                bundle.putString("Phone", phone);

                databaseReference = daoUser.getRefToCheckIfUserExists(name);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue() == null)
                        {
                            Intent intent = new Intent(getApplicationContext(), AuthenticateSignUpData.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Entered User Already Exists !. Please Try Another username !.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateUserDetails() {
        return !(!validateEmail() | !validatePassword() | !validatePhone() | !validateName());
    }

    private boolean validatePhone() {
        String val = phoneLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phoneLout.setError("Field can not be empty!");
            return false;
        } else if (val.length() != 10) {
            phoneLout.setError("Invalid Phone Number!");
            return false;
        } else {
            phoneLout.setError(null);
            phoneLout.setErrorEnabled(false);
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
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            nameLout.setError("Field can not be empty!");
            return false;
        } else if (val.length() > 20) {
            nameLout.setError("Username maximum length should be 20!");
            return false;
        } else if (!val.matches(checkSpaces)) {
            nameLout.setError("No white spaces are allowed!");
            return false;
        } else {
            nameLout.setError(null);
            nameLout.setErrorEnabled(false);
            return true;
        }

    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}