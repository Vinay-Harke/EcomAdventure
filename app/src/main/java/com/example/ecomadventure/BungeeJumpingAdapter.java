package com.example.ecomadventure;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BungeeJumpingAdapter extends RecyclerView.Adapter<BungeeJumpingAdapter.Holder>{
    private Context context;
    private int[] images;
    private int[] price;
    private int[] ratings;
    private String[] title;
    private String[] information;
    private boolean fav_flag=true;
    private ArrayList<String> getFavkey = new ArrayList<>();

    public BungeeJumpingAdapter(Context context, int[] images, int[] price, int[] ratings, String[] title, String[] information,ArrayList<String> getFavkey) {
        this.context = context;
        this.images = images;
        this.price = price;
        this.ratings = ratings;
        this.title = title;
        this.information = information;
        this.getFavkey = getFavkey;

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
        holder.textViewTitle.setText(title[position]);
        holder.ratingBar.setRating(ratings[position]);
        String str=(""+title[position]+""+position).toLowerCase().replaceAll("\\s","");
        if(checkIsFav(str)){
            Drawable buttonDrawable = holder.favoriteBtn.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            //the color is a direct color int and not a color resource
            DrawableCompat.setTint(buttonDrawable,context.getResources().getColor(R.color.pink) );
            holder.favoriteBtn.setBackground(buttonDrawable);
        }
        holder.bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("image",images[position]);
                bundle.putInt("amount",price[position]);
                bundle.putString("title",title[position]);
                Intent intent = new Intent(context,BookingDetails.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fav_flag){
                    fav_flag=false;
                    Drawable buttonDrawable = holder.favoriteBtn.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    //the color is a direct color int and not a color resource
                    DrawableCompat.setTint(buttonDrawable,view.getContext().getResources().getColor(R.color.pink) );
                    holder.favoriteBtn.setBackground(buttonDrawable);
                    Favorite favorite = new Favorite(String.valueOf(images[position]),String.valueOf(price[position]),String.valueOf(ratings[position]),title[position],information[position],HomeScreen.getUserName());
                    DaoUser daoUser = new DaoUser();
                    daoUser.addFavorites(favorite,""+title[position]+""+position);
                }
                else{

                    fav_flag=true;
                    Drawable buttonDrawable = holder.favoriteBtn.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    //the color is a direct color int and not a color resource
                    DrawableCompat.setTint(buttonDrawable,view.getContext().getResources().getColor(R.color.grey) );
                    holder.favoriteBtn.setBackground(buttonDrawable);
                    String username = HomeScreen.getUserName();
                    String key = (""+title[position]+""+position).toLowerCase().replaceAll("\\s","");
                    DaoUser daoUser = new DaoUser();
                    daoUser.removeFavorite(username,key);
                }
            }
        });
    }

    private boolean checkIsFav(String str) {
        return getFavkey.contains(str);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewInfo;
        RatingBar ratingBar;
        TextView price;
        Button bookNowBtn;
        Button favoriteBtn;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bungee_jumping_imageview);
            textViewTitle = itemView.findViewById(R.id.bj_title_textview);
            textViewInfo  = itemView.findViewById(R.id.bj_des_textview);
            ratingBar = itemView.findViewById(R.id.bj_ratingbar);
            price = itemView.findViewById(R.id.bj_price);
            bookNowBtn = itemView.findViewById(R.id.bj_book_now_btn);
            favoriteBtn = itemView.findViewById(R.id.bj_fav_btn);
        }
    }
}
