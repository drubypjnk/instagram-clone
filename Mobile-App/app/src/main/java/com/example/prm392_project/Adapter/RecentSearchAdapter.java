package com.example.prm392_project.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_project.Entity.SearchKey;
import com.example.prm392_project.Fragment.SearchFragment;
import com.example.prm392_project.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentSearchHolder> {
    List<SearchKey> searchKeys;

    SearchFragment fragment;
    public RecentSearchAdapter(List<SearchKey> searchKeys, SearchFragment fragment) {
        this.searchKeys = searchKeys;
        this.fragment = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public RecentSearchHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_row, parent, false);
        return new RecentSearchHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentSearchHolder holder, int position) {
        holder.textView.setText(searchKeys.get(position).getSearchKey());
    }

    @Override
    public int getItemCount() {
        return searchKeys == null ? 0 : searchKeys.size();
    }

    class RecentSearchHolder extends RecyclerView.ViewHolder{
        ImageView imv;
        TextView textView;
        Handler handler = new Handler();
        public RecentSearchHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.imv = itemView.findViewById(R.id.imv_recent_search);
            this.textView = itemView.findViewById((R.id.tv_recent_search));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundResource(R.color.hover_color);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            v.setBackgroundResource(R.color.default_color);
                        }
                    }, 100);

                    fragment.RecentSearchQuery((String) textView.getText());

                }
            });
        }
    }
}
