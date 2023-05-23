package com.example.prm392_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.MessageDTO;
import com.example.prm392_project.Fragment.RoomsListFragment;
import com.example.prm392_project.Model.Message;
import com.example.prm392_project.R;
import com.example.prm392_project.Utils.UtilImage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{
    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;
    List<MessageDTO> messages;
    Context context;
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_receive_row, parent, false);
            return new MessageHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
            return new MessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.tv_message_content.setText(messages.get(position).getMessage().getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public MessageAdapter(Context context, List<MessageDTO> messages){
        this.messages = messages;
        this.context = context;
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        public TextView tv_message_content;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getAuthor().equals(MyApp.GetUserId())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
