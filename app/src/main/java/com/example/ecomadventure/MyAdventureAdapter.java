package com.example.ecomadventure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class MyAdventureAdapter extends RecyclerView.Adapter<MyAdventureAdapter.Holder> {

    private Context context;
    private int[] imageId;
    private String [] title;
    private String []transaction_status;
    private int[] noOfTicket;
    private int[] billAmount;
    private String[] transactionId;

    public MyAdventureAdapter(Context context, int[] imageId, String[] title, String[] transaction_status, int[] noOfTicket, int[] billAmount, String[] transactionId) {
        this.context = context;
        this.imageId = imageId;
        this.title = title;
        this.transaction_status = transaction_status;
        this.noOfTicket = noOfTicket;
        this.billAmount = billAmount;
        this.transactionId = transactionId;
    }

    @NonNull
    @Override
    public MyAdventureAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_adventure_fragment_recycler_item, parent, false);
        return new MyAdventureAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdventureAdapter.Holder holder, int position) {
        holder.imageView.setImageResource(imageId[position]);
        holder.titleView.setText(title[position]);
        holder.transactionStatusView.setText(transaction_status[position]);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("imageId",String.valueOf(imageId[position]));
                bundle.putString("title",title[position]);
                bundle.putString("no_of_ticket",String.valueOf(noOfTicket[position]));
                bundle.putString("bill_amount",String.valueOf(billAmount[position]));
                bundle.putString("transaction_id",transactionId[position]);
                Intent intent = new Intent(context,TicketDetail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageId.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView transactionStatusView;
        MaterialCardView materialCardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.my_adventure_image);
            titleView = itemView.findViewById(R.id.my_adventure_title_textview);
            transactionStatusView = itemView.findViewById(R.id.my_adventure_transaction_status);
            materialCardView = itemView.findViewById(R.id.adventure_card_clickable);
        }
    }
}
