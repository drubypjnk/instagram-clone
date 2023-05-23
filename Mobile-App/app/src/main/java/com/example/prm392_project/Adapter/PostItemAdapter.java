package com.example.prm392_project.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.DTO.PostItemDTO;
import com.example.prm392_project.DTO.UserToManageDTO;
import com.example.prm392_project.Fragment.PostManagerFragment;
import com.example.prm392_project.Fragment.UserManagerFragment;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostItemAdapter extends RecyclerView.Adapter<PostItemAdapter.PostItemHolder> {
    List<PostItemDTO> postItemDTOS;
    CallbackFragment callbackFragment;

    public PostItemAdapter(List<PostItemDTO> postItemDTOS, CallbackFragment callbackFragment) {
        this.postItemDTOS = postItemDTOS;
        this.callbackFragment = callbackFragment;
    }

    @NonNull
    @Override
    public PostItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_post_item, parent, false);
        return new PostItemAdapter.PostItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostItemHolder holder, int position) {
        PostItemDTO item = postItemDTOS.get(position);
        String messN = "", messY = "";
        if (item == null) {
            return;
        }
        holder.title.setText("Name: " + item.getTitle());
        if (item.getImage()==null){
            holder.imv_avatar.setImageResource(R.drawable.baseline_account_circle_24);
        }else {
            holder.imv_avatar.setImageBitmap(base64ToBitmap(item.getImage()));
        }
        holder.author.setText("Autor: " + item.getUserId());
        if (item.isDelete == true) {
            messY = "Post: " + item.getTitle() + " be disable!";
            holder.btn_action.setText("Disable");
            holder.btn_action.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.status.setText("Enable");
            holder.status.setTextColor(Color.parseColor("#00FF00"));
            holder.status.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));

        } else {
            messY = "Post: " + item.getTitle() + " be enable!";
            holder.btn_action.setText("Enable");
            holder.btn_action.setBackgroundColor(Color.parseColor("#00FF00"));
            holder.status.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            holder.status.setText("Disable");
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }
        String finalMessY = messY;
        holder.btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to disable post '" + item.getTitle() + "'");
                holder.builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (item.isDelete == true) {
                            deactiveUser(item.getPostId(), finalMessY);
                        } else {
                            activeUser(item.getPostId(), finalMessY);
                        }
                    }

                    private void activeUser(int userId, String mess) {
                        ApiService.apiService.activePost(userId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                boolean isRegisted = response.body();
                                if (isRegisted == true) {
                                    Toast.makeText(holder.itemView.getContext(), mess, Toast.LENGTH_SHORT).show();
                                    callbackFragment.changeFragment(new UserManagerFragment());

                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "Enable Post Error!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    }

                    private void deactiveUser(int userId, String mess) {
                        ApiService.apiService.deActivePost(userId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Boolean isRegisted = response.body();
                                if (isRegisted == true) {
                                    Toast.makeText(holder.itemView.getContext(), mess, Toast.LENGTH_SHORT).show();
                                    callbackFragment.changeFragment(new PostManagerFragment());
                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "Disable Post Error!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    }
                });
                holder.builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.itemView.getContext(), "Cancled!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = holder.builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return postItemDTOS != null ? postItemDTOS.size() : 0;

    }

    public class PostItemHolder extends RecyclerView.ViewHolder {

        AlertDialog.Builder builder;

        ImageView imv_avatar;

        TextView title, author, status;

        Button btn_action;
        public PostItemHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_item);
            author = itemView.findViewById(R.id.tv_author_item);
            status = itemView.findViewById(R.id.tv_post_status);
            btn_action = itemView.findViewById(R.id.btn_action);
            imv_avatar = itemView.findViewById(R.id.imv_post_item);
            builder = new AlertDialog.Builder(itemView
                    .getContext());
        }
    }
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return bitmap;
    }
}
