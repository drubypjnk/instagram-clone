package com.example.prm392_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_project.Model.ActivityContainer;
import com.example.prm392_project.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActivityContainerAdapter extends RecyclerView.Adapter<ActivityContainerAdapter.ActivityContainerHolder> {

    List<ActivityContainer> activityContainers;
    Context context;
    public ActivityContainerAdapter(List<ActivityContainer> activityContainers, Context context) {
        this.activityContainers = activityContainers;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ActivityContainerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_container, parent, false);
        return new ActivityContainerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActivityContainerHolder holder, int position) {
        ActivityContainer parent = activityContainers.get(position);
        if(parent.getActivityItemList() != null && parent.getActivityItemList().size() > 0){
            holder.tv.setText(parent.getTitle());

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.rec.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(parent.getActivityItemList().size());

            ActivityItemAdapter activityItemAdapter = new ActivityItemAdapter(parent.getActivityItemList(), context);
            holder.rec.setLayoutManager(layoutManager);
            holder.rec.setAdapter(activityItemAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return activityContainers != null ? activityContainers.size() : 0;
    }

    class ActivityContainerHolder extends RecyclerView.ViewHolder{
        TextView tv;
        RecyclerView rec;

        public ActivityContainerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_notification_container_title);
            rec = itemView.findViewById(R.id.rec_activity_container);
        }
    }
}
