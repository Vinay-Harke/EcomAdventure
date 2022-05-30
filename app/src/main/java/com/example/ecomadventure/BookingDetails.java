package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class BookingDetails extends AppCompatActivity {

    private Button incrBtn;
    private Button decrBtn;
    private Button makePaymnet;
    private TextView no_of_ticket_txtView;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private String selectedDate;
    private int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Bundle bundleFromHomeFragment = getIntent().getExtras();
        incrBtn = findViewById(R.id.ticketIncrBtn);
        decrBtn = findViewById(R.id.ticketDecrBtn);
        dateView = findViewById(R.id.booking_date);
        makePaymnet = findViewById(R.id.makePaymentBtn);
        no_of_ticket_txtView = findViewById(R.id.no_of_ticket_txtview);
        no_of_ticket_txtView.setText("1");
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mdate) {
                        selectedDate = mdate+"/"+(mmonth+1)+"/"+myear;
                        dateView.setText(selectedDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        makePaymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(no_of_ticket_txtView.getText().toString().trim());
                bundleFromHomeFragment.putString("Date",selectedDate);
                bundleFromHomeFragment.putInt("NumberOfTicket",number);
                Intent intent = new Intent(getApplicationContext(),MakePayment.class);
                intent.putExtras(bundleFromHomeFragment);
                startActivity(intent);
            }
        });
    }
    public void incrementTicketCount(View view) {
        int number = Integer.parseInt(no_of_ticket_txtView.getText().toString().trim());
        if(number < 10)
            no_of_ticket_txtView.setText(String.valueOf(++number));
    }

    public void decrementTicketCount(View view) {
        int number = Integer.parseInt(no_of_ticket_txtView.getText().toString().trim());
        if(number > 1)
            no_of_ticket_txtView.setText(String.valueOf(--number));
    }
}