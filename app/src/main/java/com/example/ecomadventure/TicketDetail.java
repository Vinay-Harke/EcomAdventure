package com.example.ecomadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketDetail extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleText,noOfTicketText,billAmountText,transactionText;
    private String imageId,title,noOfTicket,billAmount,transactionId;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        imageView = findViewById(R.id.ticketdetail_image);
        titleText = findViewById(R.id.ticket_detail_title);
        noOfTicketText = findViewById(R.id.ticket_detail_no_of_ticket);
        billAmountText = findViewById(R.id.ticket_detail_bill_amount);
        transactionText = findViewById(R.id.ticket_detail_transaction_id);

        bundle = getIntent().getExtras();

        if(bundle != null){
        imageId = bundle.getString("imageId");
        title = bundle.getString("title");
        noOfTicket=bundle.getString("no_of_ticket");
        billAmount = bundle.getString("bill_amount");
        transactionId=bundle.getString("transaction_id");}

        imageView.setImageResource(Integer.parseInt(imageId));
        titleText.setText(title);
        noOfTicketText.setText("No of Ticket : "+noOfTicket);
        billAmountText.setText("Bill : "+billAmount);
        transactionText.setText(transactionId);
    }
}