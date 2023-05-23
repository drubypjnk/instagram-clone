package com.example.prm392_project.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.prm392_project.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context context;
    private List<String> images;
    protected PhotoListener photoListener;

    public GalleryAdapter(Context context, List<String> images, PhotoListener photoListener) {
        this.context = context;
        this.images = images;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.image_view_item, parent, false)
        );
    }

    int selected_position = 0;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String image = images.get(position);
        File file = new File(image);
        boolean exist = file.exists();
        boolean canRead = file.canRead();
        if (exist && canRead) {
            Glide.with(context).load(image).into(holder.image);
        } else {
            // File does not exist or is not readable
        }

        if (selected_position == position) {
            holder.itemView.setAlpha(0.2f);
        } else {
            holder.itemView.setAlpha(1);
        }
//        if (position == 0) holder.itemView.performClick();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoListener.onPhotoClick(image);
                if (selected_position == position) {
                    selected_position = -1;
                    notifyDataSetChanged();
                    return;
                }
                selected_position = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            image = itemView.findViewById(R.id.imageViewItem);
        }
    }

    public interface PhotoListener {
        void onPhotoClick(String path);
    }

}
