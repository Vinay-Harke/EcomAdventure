package com.example.ecomadventure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class BungeeJumpingAdapter extends RecyclerView.Adapter<BungeeJumpingAdapter.Holder> implements PaymentResultListener {
    private Context context;
    private int[] images;
    private int[] price;
    private int[] ratings;
    private String[] title;
    private String[] information;

    public BungeeJumpingAdapter(Context context, int[] images, int[] price, int[] ratings, String[] title, String[] information) {
        this.context = context;
        this.images = images;
        this.price = price;
        this.ratings = ratings;
        this.title = title;
        this.information = information;
    }

    @NonNull
    @Override
    public BungeeJumpingAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bungee_jumping_image_recycler, parent, false);
        return new BungeeJumpingAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BungeeJumpingAdapter.Holder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.price.setText(Integer.toString(price[position]));
        holder.textViewInfo.setText(information[position]);
        holder.textViewTitle.setText(information[position]);
        holder.ratingBar.setRating(ratings[position]);
        holder.bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout.preload(context);
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_pHKXIfwdACecCv");

                final Activity activity  = (Activity) view.getContext();


                // initialize json object
                try {
                    JSONObject object = new JSONObject();
                    // to put name
                    object.put("name", "Viayak Harke");

                    // put description
                    object.put("description", "Bhadwaa");
                    // object.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3

                    // to set theme color
                    object.put("theme.color", "#EC9706");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    int amount = Math.round(price[position]*100);
                    object.put("amount",amount);
                    //object.put("prefill.email", "gaurav.kumar@example.com");
                    //object.put("prefill.contact","9988776655");

                    JSONObject retryObj = new JSONObject();
                    retryObj.put("enabled", true);
                    retryObj.put("max_count", 4);
                    object.put("retry", retryObj);

                    checkout.open((Activity) activity, object);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(),information[position],Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return images.length;
    }

    @Override
    public void onPaymentSuccess(String s) {
        //Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,PdfDownload.class);
        context.startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        //Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_LONG).show();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewInfo;
        RatingBar ratingBar;
        TextView price;
        Button bookNowBtn;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bungee_jumping_imageview);
            textViewTitle = itemView.findViewById(R.id.bj_title_textview);
            textViewInfo  = itemView.findViewById(R.id.bj_des_textview);
            ratingBar = itemView.findViewById(R.id.bj_ratingbar);
            price = itemView.findViewById(R.id.bj_price);
            bookNowBtn = itemView.findViewById(R.id.bj_book_now_btn);

        }
    }
}
