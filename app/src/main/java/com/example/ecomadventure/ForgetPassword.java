package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {
    TextInputLayout usernameLout;
    Button submitBtn;
    DaoUser daoUser;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        usernameLout = findViewById(R.id.forget_password_username_id);
        submitBtn = findViewById(R.id.forget_pwd_submit_btn);
        daoUser = new DaoUser();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUserData()){
                    usernameLout.setError(null);
                    usernameLout.setErrorEnabled(false);
                    String userName = usernameLout.getEditText().getText().toString().trim();
                    databaseReference =daoUser.getRefToCheckIfUserExists(userName);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {
                                Toast.makeText(getApplicationContext(), "No Such User Exists!", Toast.LENGTH_LONG).show();}
                            else{
                                Intent intent = new Intent(getApplicationContext(),ForgetPasswordOtpVerification.class);
                                intent.putExtra("username",userName);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


    private boolean validateUserData() {
        String val = usernameLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            usernameLout.setError("Field can't be empty");
            return false;
        } else {
            usernameLout.setError(null);
            usernameLout.setErrorEnabled(false);
            return true;
        }
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}