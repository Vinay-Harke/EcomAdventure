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
import java.util.Map;

public class FavoriteFragmentAdapter extends RecyclerView.Adapter<FavoriteFragmentAdapter.FavHolder> {
    private Context context;
    private int[] images;
    private int[] price;
    private int[] ratings;
    private String[] title;
    private String[] information;
    private ArrayList<String> getFavkey = new ArrayList<>();

    public FavoriteFragmentAdapter(Context context, int[] images, int[] price, int[] ratings, String[] title, String[] information,ArrayList
                                   <String> getFavkey) {
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
    public FavoriteFragmentAdapter.FavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_fragment_image_recycle, parent, false);
        return new FavoriteFragmentAdapter.FavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFragmentAdapter.FavHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.price.setText(Integer.toString(price[position]));
        holder.textViewInfo.setText(information[position]);
        holder.textViewTitle.setText(title[position]);
        holder.ratingBar.setRating(ratings[position]);
        String str=(""+title[position]+""+position).toLowerCase().replaceAll("\\s","");
        Drawable buttonDrawable = holder.favoriteBtn.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable,context.getResources().getColor(R.color.pink) );
        holder.favoriteBtn.setBackground(buttonDrawable);
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
                Drawable buttonDrawable = holder.favoriteBtn.getBackground();
                buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                //the color is a direct color int and not a color resource
                DrawableCompat.setTint(buttonDrawable,context.getResources().getColor(R.color.grey) );
                holder.favoriteBtn.setBackground(buttonDrawable);
                //delteFromFavorites();
                String username = HomeScreen.getUserName();
                String key = getFavkey.get(position);
                DaoUser daoUser = new DaoUser();
                daoUser.removeFavorite(username,key);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class FavHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewInfo;
        RatingBar ratingBar;
        TextView price;
        Button bookNowBtn;
        Button favoriteBtn;

        public FavHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_frag_imageview);
            textViewTitle = itemView.findViewById(R.id.fav_frag_title_textview);
            textViewInfo = itemView.findViewById(R.id.fav_fragment_des_textview);
            ratingBar = itemView.findViewById(R.id.fav_frag_ratingbar);
            price = itemView.findViewById(R.id.fav_frag_price);
            bookNowBtn = itemView.findViewById(R.id.fav_frag_book_now_btn);
            favoriteBtn = itemView.findViewById(R.id.fav_frag_fav_btn);

        }
    }
}

