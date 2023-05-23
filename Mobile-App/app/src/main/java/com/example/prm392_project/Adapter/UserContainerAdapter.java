package com.example.prm392_project.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Model.ActivityContainer;
import com.example.prm392_project.Model.UserContainer;
import com.example.prm392_project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserContainerAdapter extends RecyclerView.Adapter<UserContainerAdapter.UserContainerHolder>{

    List<UserContainer> userContainers;

    public UserContainerAdapter(List<UserContainer> activityContainers) {
        this.userContainers = activityContainers;
    }

    @NonNull
    @Override
    public UserContainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserContainerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class UserContainerHolder extends RecyclerView.ViewHolder{
        TextView tv;
        RecyclerView rec;

        public UserContainerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_notification_container_title);
            rec = itemView.findViewById(R.id.rec_activity_container);
        }
    }
}
