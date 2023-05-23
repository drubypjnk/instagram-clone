package com.example.prm392_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.DTO.RoomDTO;
import com.example.prm392_project.R;
import com.example.prm392_project.Utils.Common;
import com.example.prm392_project.Utils.UtilImage;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {
    public List<RoomDTO> rooms;
    public Context context;

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_row, parent, false);
        return new RoomHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        holder.imv_room_row_avatar.setImageBitmap(UtilImage.getImageBase64(rooms.get(position).getReceiver().getAvartarImage().getContent()));
        holder.tv_room_row_title.setText(rooms.get(position).getRoom().getRoomTitle());
        holder.tv_room_row_last_massage.setText(rooms.get(position).getLastMassage().getContent());
        holder.tv_room_row_time.setText(Common.formatDateWithCurrentTime(rooms.get(position).getLastMassage().getCreatedDate()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                MainActivity.getInstance().redirectToMessageListFragment(rooms.get(position).getRoom().getRoomId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public RoomAdapter(@Nullable Context context, List<RoomDTO> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    class RoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imv_room_row_avatar;
        public TextView tv_room_row_title;
        public TextView tv_room_row_last_massage;
        public TextView tv_room_row_time;
        ItemClickListener itemClickListener;
        public RoomHolder(@NonNull View itemView) {
            super(itemView);
            imv_room_row_avatar = itemView.findViewById(R.id.imv_room_row_avatar);
            tv_room_row_title = itemView.findViewById(R.id.tv_room_row_title);
            tv_room_row_last_massage = itemView.findViewById(R.id.tv_room_row_last_massage);
            tv_room_row_time = itemView.findViewById(R.id.tv_room_row_time);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int adapterPosition, boolean b);
    }
}
