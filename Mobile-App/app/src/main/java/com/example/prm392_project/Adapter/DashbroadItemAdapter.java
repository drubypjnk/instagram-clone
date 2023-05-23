package com.example.prm392_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Fragment.PostManagerFragment;
import com.example.prm392_project.Fragment.UserManagerFragment;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.Model.DashbroadItem;
import com.example.prm392_project.R;

import java.util.List;

public class DashbroadItemAdapter extends RecyclerView.Adapter<DashbroadItemAdapter.DashbroadItemViewHolder> {
    private List<DashbroadItem> dashbroadItems;
    private CallbackFragment mListener;


    public DashbroadItemAdapter(List<DashbroadItem> dashbroadItems, CallbackFragment mListener) {
        this.dashbroadItems = dashbroadItems;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DashbroadItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashbroad_item, parent,false);
        return new DashbroadItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashbroadItemViewHolder holder, int position) {
        DashbroadItem item = dashbroadItems.get(position);
        if (item==null){
            return;
        }
        holder.imgItem.setImageResource(item.getImage());
        holder.tvNameItem.setText(item.getName());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (item.getType()){
                        case 1:
                            mListener.changeFragment(new UserManagerFragment());
                            return;
                        case 2:
                            mListener.changeFragment(new PostManagerFragment());
                            return;
                        case 3:
                            holder.editor.putString("user_id", null);
                            holder.editor.apply();
                            holder.editor.commit();
                            Toast.makeText(holder.itemView.getContext(), "Logged out!", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(holder.itemView.getContext(), MainActivity.class);
                            holder.itemView.getContext().startActivity(myIntent);
                            ((MainActivity)holder.itemView.getContext()).finish();
                            MyApp.activityContainers = null;
                            return;
                    }
                }
            });
        }


    @Override
    public int getItemCount() {

        return dashbroadItems != null ? dashbroadItems.size() : 0;
    }

    public class DashbroadItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvNameItem;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        private CardView cardView;

        FragmentTransaction fragmentTransaction;

        public DashbroadItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_dbr_item);
            tvNameItem = itemView.findViewById(R.id.tv_dbr_item);
            cardView = itemView.findViewById(R.id.layout_item);

            sharedPreferences = itemView.getContext().getSharedPreferences("userFile", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();


        }
    }
}
