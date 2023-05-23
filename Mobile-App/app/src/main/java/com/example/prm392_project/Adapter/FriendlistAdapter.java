package com.example.prm392_project.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.DTO.FollowInforDTO;
import com.example.prm392_project.R;

import java.util.ArrayList;
import java.util.List;

public class FriendlistAdapter extends RecyclerView.Adapter<FriendlistAdapter.FriendItemViewHolder> {
    private List<FollowInforDTO> list;
    private ButtonItemListener buttonItemListener;
    private String currentUser;

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public FriendlistAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public FriendItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendItemViewHolder holder, int position) {
        FollowInforDTO friend = list.get(position);
        holder.image.setImageBitmap(getImageBase64(friend.getAvatar()));
        holder.tvName.setText(friend.getFullName());
        holder.tvUsername.setText(friend.getUserName());

        //setup button
            if(friend.isFlag()){
                holder.btUnfollow.setBackgroundColor(Color.GRAY);
                holder.btUnfollow.setText("Unfollow");
            }else{
                holder.btUnfollow.setBackgroundColor(Color.BLUE);
                holder.btUnfollow.setText("Follow");
            }
            if(friend.getUserId().equals(currentUser)){
                holder.btUnfollow.setVisibility(Button.INVISIBLE);
            }
    }

    public void setItemListener(ButtonItemListener buttonItemListener) {
        this.buttonItemListener = buttonItemListener;
    }

    public void setList(List<FollowInforDTO> list) {
        this.list = list;

    }

    public Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public FollowInforDTO getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FriendItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUsername, tvName;
        private ImageView image;
        private Button btUnfollow;

        public FriendItemViewHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.img_friend_item);
            tvUsername = view.findViewById(R.id.tv_friend_username_item);
            tvName = view.findViewById(R.id.tv_friend_name_item);
            btUnfollow = view.findViewById(R.id.btn_unFollow);
            btUnfollow.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    buttonItemListener.onViewFollowClick(view,getAdapterPosition());
                    int position = getAdapterPosition();
                    FollowInforDTO followInforDTO = list.get(position);
                    MainActivity.getInstance().redirectToUserProfileFragment(followInforDTO.getUserId());
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (buttonItemListener != null) {
                buttonItemListener.onButtonClick(view, getAdapterPosition(),btUnfollow);
            }
        }
    }

    public interface ButtonItemListener {
        void onButtonClick(View view, int position,Button button);
//        void onViewFollowClick(View view,int position);
    }
}
