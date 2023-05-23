package com.example.prm392_project.Adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.DTO.PostInforDTO;
import com.example.prm392_project.Model.PostImg;
import com.example.prm392_project.R;

import java.util.ArrayList;
import java.util.List;

public class ImagePostAdapter extends RecyclerView.Adapter<ImagePostAdapter.ImagePostViewHolder> {
    private List<PostInforDTO> list;
    private ItemListener itemListener;

    public ImagePostAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ImagePostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_post_item, parent, false);
        return new ImagePostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePostViewHolder holder, int position) {
        PostInforDTO post = list.get(position);
//        holder.img.setBackgroundResource(post.getImage());
        try {
            holder.img.setImageBitmap(getImageBase64(post.postImage.getContent()));
        } catch (Exception e) {
            holder.img.setImageResource(R.drawable.empty_image);
            e.printStackTrace();
        }
    }

    public Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<PostInforDTO> list) {
        this.list = list;
    }

    public PostInforDTO getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImagePostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;

        public ImagePostViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_post_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemclick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemclick(View view, int position);
    }
}
