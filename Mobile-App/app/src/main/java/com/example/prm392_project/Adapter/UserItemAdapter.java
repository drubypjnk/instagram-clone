package com.example.prm392_project.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.DTO.UserToManageDTO;
import com.example.prm392_project.Fragment.UserManagerFragment;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UserItemHolder> {

    List<UserToManageDTO> userItemAdapters;
    private CallbackFragment mListener;

    public UserItemAdapter(List<UserToManageDTO> userItemAdapters, CallbackFragment mListener) {
        this.userItemAdapters = userItemAdapters;
        this.mListener = mListener;
    }

    @NonNull
    @NotNull
    @Override
    public UserItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new UserItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserItemHolder holder, int position) {
        UserToManageDTO item = userItemAdapters.get(position);
        String messN = "", messY = "";
        if (item == null) {
            return;
        }
        holder.fullName.setText("Name: " + item.fullName);
        if (item.getAvartarImage().getContent()==null){
            holder.imv_avatar.setImageResource(R.drawable.baseline_account_circle_24);
        }else {
            holder.imv_avatar.setImageBitmap(base64ToBitmap(userItemAdapters.get(position).getAvartarImage().getContent()));
        }
        holder.email.setText("Email: " + item.getEmail());
        if (item.isActive == true) {
            messY = "User: " + item.fullName + " be deactivated!";
            holder.btn_fl.setText("Deactive");
            holder.btn_fl.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.status.setText("Active");
            holder.status.setTextColor(Color.parseColor("#00FF00"));
            holder.status.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));

        } else {
            messY = "User: " + item.fullName + " be activated!";
            holder.btn_fl.setText("Active");
            holder.btn_fl.setBackgroundColor(Color.parseColor("#00FF00"));
            holder.status.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            holder.status.setText("InActive");
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }
        String finalMessY = messY;
        holder.btn_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to Deactive user '" + item.fullName + "'");
                holder.builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (item.isActive == true) {
                            deactiveUser(item.getUserId(), finalMessY);
                        } else {
                            activeUser(item.getUserId(), finalMessY);
                        }
                    }

                    private void activeUser(String userId, String mess) {
                        ApiService.apiService.activeUser(userId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                boolean isRegisted = response.body();
                                if (isRegisted == true) {
                                    Toast.makeText(holder.itemView.getContext(), mess, Toast.LENGTH_SHORT).show();
                                    mListener.changeFragment(new UserManagerFragment());

                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "Active User Error!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    }

                    private void deactiveUser(String userId, String mess) {
                        ApiService.apiService.deActiveUser(userId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Boolean isRegisted = response.body();
                                if (isRegisted == true) {
                                    Toast.makeText(holder.itemView.getContext(), mess, Toast.LENGTH_SHORT).show();
                                    mListener.changeFragment(new UserManagerFragment());
                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "DeActive User Error!", Toast.LENGTH_SHORT).show();
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
        return userItemAdapters != null ? userItemAdapters.size() : 0;
    }

    public class UserItemHolder extends RecyclerView.ViewHolder {
        AlertDialog.Builder builder;

        ImageView imv_avatar;

        TextView fullName, email, status;

        Button btn_fl;

        public UserItemHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tv_uname);
            email = itemView.findViewById(R.id.tv_uemail);
            status = itemView.findViewById(R.id.tv_ustatus);
            btn_fl = itemView.findViewById(R.id.btn_details);
            imv_avatar = itemView.findViewById(R.id.imv_avatar);
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
