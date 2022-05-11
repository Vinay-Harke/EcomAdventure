package com.example.ecomadventure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDetailsImageAdapter extends RecyclerView.Adapter<ViewDetailsImageAdapter.DetailsHolder> {
    private Context context;
    private int[] images;

    public ViewDetailsImageAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_details_images_recycle_item, parent, false);
        return new ViewDetailsImageAdapter.DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDetailsImageAdapter.DetailsHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
    public class DetailsHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public DetailsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_details_image);
        }
    }
}
