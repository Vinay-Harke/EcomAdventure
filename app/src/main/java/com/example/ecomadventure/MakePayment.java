package com.example.ecomadventure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MakePayment extends AppCompatActivity implements PaymentResultListener {

    Bundle bundleFromHomeFragment;
    private int noOfTickets;
    private int amount;
    private long billAmount;
    private String title;

    private String username;
    private static String  email;
    private static String  phoneNo;
    private String transactionimage;
    public  static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MakePayment.email = email;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(String phoneNo) {
        MakePayment.phoneNo = phoneNo;
    }

    private DaoUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        bundleFromHomeFragment = getIntent().getExtras();

        noOfTickets = bundleFromHomeFragment.getInt("NumberOfTicket");
        amount = bundleFromHomeFragment.getInt("amount");
        billAmount = noOfTickets*amount*100;
        title = bundleFromHomeFragment.getString("title");
        transactionimage = String.valueOf(bundleFromHomeFragment.getInt("image"));
        username = HomeScreen.getUserName();
        daoUser = new DaoUser();
        User user = new User(username);

        Query checkUser = daoUser.validateUserCredentials(user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists())
               {
                    String mPhoneNo = snapshot.child(username).child("phone").getValue(String.class);
                    String mEmail = snapshot.child(username).child("email").getValue(String.class);
                    startPayment(mEmail,mPhoneNo);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startPayment(String mEmail, String mPhoneNo) {

        MakePayment.setEmail(mEmail);
        MakePayment.setPhoneNo(mPhoneNo);

        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_pHKXIfwdACecCv");

        final Activity activity  = this;

        // initialize json object
        try {
            JSONObject object = new JSONObject();
            // to put name
            String des = username+"\n"+mEmail;
            object.put("name", des);

            // put description
            object.put("description", title);
            // object.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3

            // to set theme color
            object.put("theme.color", "#EC9706");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount",billAmount);
            object.put("prefill.email", mEmail);

            if(mPhoneNo != null)
            {
                String mobileNo = mPhoneNo.substring(3);
                object.put("prefill.contact",mobileNo);
            }
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            object.put("retry", retryObj);

            checkout.open((Activity) activity, object);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Payment Started",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Intent intent = new Intent(getApplicationContext(),PdfDownload.class);
        bundleFromHomeFragment.putString("TransactionId",s);
        bundleFromHomeFragment.putString("username",username);
        bundleFromHomeFragment.putString("mobile",MakePayment.getPhoneNo());
        bundleFromHomeFragment.putString("email",MakePayment.getEmail());
        intent.putExtras(bundleFromHomeFragment);
        registerTransaction("Success",s);
        startActivity(intent);
    }

    private void registerTransaction(String success,String transactionId) {
        Transactions transactions = new Transactions(username,title,transactionimage,String.valueOf(noOfTickets),String.valueOf(billAmount/100),transactionId,success);
        DaoUser daoUser = new DaoUser();
        daoUser.addTransaction(transactions).addOnSuccessListener(suc ->{


        }).addOnFailureListener(er ->{

        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        registerTransaction("Failed",s);
    }
}