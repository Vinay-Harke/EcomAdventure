package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    TextInputLayout userNameLout, passwordLout;
    MaterialCardView login_card_view;
    ImageButton googleLoginBtn;
    private Button loginBtn;
    private DaoUser daoUser;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private static final int RC_SIGN_IN=101;
    private String userName,password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameLout = findViewById(R.id.login_username);
        passwordLout = findViewById(R.id.login_password);
        login_card_view = findViewById(R.id.login_card);
        googleLoginBtn = findViewById(R.id.google_login_btn);
        loginBtn = findViewById(R.id.login_btn);
        daoUser = new DaoUser();
        userNameLout.animate().alpha(1).setDuration(1600).translationX(20);
        passwordLout.animate().alpha(1).setDuration(1700).translationX(20);
        login_card_view.animate().alpha(1).setDuration(1500).translationY(100);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSign();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUserDetails()) {
                    userName = userNameLout.getEditText().getText().toString().trim();
                    password = passwordLout.getEditText().getText().toString().trim();
                    User newUser = new User(userName,password);
                    Query checkUser = daoUser.validateUserCredentials(newUser);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                userNameLout.setError(null);
                                userNameLout.setErrorEnabled(false);
                                String systemPassword = snapshot.child(userName).child("password").getValue(String.class);
                                if (systemPassword.equals(password)) {
                                    passwordLout.setError(null);
                                    passwordLout.setErrorEnabled(false);
                                    Toast.makeText(getApplicationContext(), "Login Successful !", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("username",userName);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password Does Not Match !", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "No Such User Exists !", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }

    private void googleSign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(googleSignInAccount.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerDataOfGoogleLoginUser(user);
                            updateUI(user);
                        }else{
                            Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        String name = user.getDisplayName();
        String userName = name.toLowerCase().replaceAll("\\s","");
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("username",name);
        startActivity(intent);
        finish();
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public boolean validateUserDetails() {
        return !(!validateUsername() | !validatePassword());
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

    private boolean validateUsername() {
        String val = userNameLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            userNameLout.setError("Field can't be empty");
            return false;
        } else {
            userNameLout.setError(null);
            userNameLout.setErrorEnabled(false);
            return true;
        }
    }

    public void callForgetPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);

    }

    private void registerDataOfGoogleLoginUser(FirebaseUser firebaseUser){
        String name = firebaseUser.getDisplayName();

        String username = name.toLowerCase();
        String email = firebaseUser.getEmail();
        String password = null;
        String phone = firebaseUser.getPhoneNumber();

        User user = new User(username,email,phone,password);
        DaoUser daoUser = new DaoUser();
        daoUser.add(user).addOnSuccessListener(suc ->{
            Toast.makeText(getApplicationContext(), "Registration done successfully !", Toast.LENGTH_LONG).show();

        }).addOnFailureListener(er ->
        {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        });
    }

}