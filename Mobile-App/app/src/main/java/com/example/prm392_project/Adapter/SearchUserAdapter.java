package com.example.prm392_project.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.R;
import com.example.prm392_project.Utils.ImageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder> {
    List<UserInforDTO> userInforDTOList;

    public SearchUserAdapter(List<UserInforDTO> userInforDTOList) {
        this.userInforDTOList = userInforDTOList;
    }

    @NonNull
    @NotNull
    @Override
    public SearchUserHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new SearchUserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchUserHolder holder, int position) {
        holder.textView.setText(userInforDTOList.get(position).getFullName());
        holder.imv.setImageBitmap(ImageUtils.getImageBase64(userInforDTOList.get(position).getAvartarImage().getContent()));
        holder.tv_username.setText(userInforDTOList.get(position).getUsername());
        holder.tv_userId.setText(userInforDTOList.get(position).getUserId());
    }

    @Override
    public int getItemCount() {
        return userInforDTOList != null ? userInforDTOList.size() : 0;
    }

    class SearchUserHolder extends RecyclerView.ViewHolder{
        ImageView imv;
        TextView tv_username;
        TextView tv_userId;
        TextView textView;
        Handler handler = new Handler();
        public SearchUserHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imv_search_row);
            textView = itemView.findViewById(R.id.tv_search_name);
            tv_username = itemView.findViewById((R.id.tv_search_user_name));
            tv_userId = itemView.findViewById(R.id.tv_search_user_id);
            tv_userId.setVisibility(View.INVISIBLE);
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

                    MainActivity.getInstance().redirectToUserProfileFragment((String) tv_userId.getText());
                }
            });
        }

    }
}
