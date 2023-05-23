package com.example.prm392_project.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.Fragment.PostDetailFragment;
import com.example.prm392_project.Model.ActivityItem;
import com.example.prm392_project.R;
import com.example.prm392_project.Utils.ImageUtils;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ActivityItemAdapter extends RecyclerView.Adapter<ActivityItemAdapter.ActivityItemHolder> {

    List<ActivityItem> activityItemList;
    Context context;

    public ActivityItemAdapter(List<ActivityItem> activityItemList, Context context) {
        this.activityItemList = activityItemList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ActivityItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row, parent, false);
        return new ActivityItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActivityItemHolder holder, int position) {
//        Glide.with(context).load(activityItemList.get(position).getImageSource()).circleCrop().into(holder.imv_avatar);
        holder.imv_avatar.setImageBitmap(ImageUtils.getImageBase64(activityItemList.get(position).getImageSource()));
//        if(!activityItemList.get(position).isFollowNotification()){
            holder.btn_fl.setVisibility(View.GONE);
            int widthInDp = 295; // replace with your desired width in dp
            int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp, context.getResources().getDisplayMetrics());
            holder.des.setWidth(widthInPx);
//        }
        holder.des.setText((CharSequence) activityItemList.get(position).getContent());
        holder.tv_target.setText(activityItemList.get(position).getPostId());
        holder.tv_isFollow.setText(activityItemList.get(position).isFollowNotification() ? "t" : "f");
    }

    @Override
    public int getItemCount() {
        return activityItemList != null ? activityItemList.size() : 0;
    }

    class ActivityItemHolder extends RecyclerView.ViewHolder{
        ImageView imv_avatar;

        TextView des;

        Button btn_fl;

        TextView tv_target;
        TextView tv_isFollow;
        public ActivityItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imv_avatar = itemView.findViewById(R.id.imv_avatar);
            des = itemView.findViewById(R.id.tv_desription);
            btn_fl = itemView.findViewById(R.id.btn_follow);
            tv_target = itemView.findViewById(R.id.tv_target);
            tv_isFollow = itemView.findViewById(R.id.tv_isFollow);
            tv_target.setVisibility(View.INVISIBLE);
            tv_isFollow.setVisibility(View.INVISIBLE);

            Handler handler = new Handler();
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

                    String isFollow = (String) tv_isFollow.getText();
                    if(isFollow.equals("f")){
                        MainActivity main = MainActivity.getInstance();
                        main.setViewDetailPost(true, (String) tv_target.getText());
                        main.nv.setSelectedItemId(R.id.nv_home);
                    } else {
                        MainActivity.getInstance().redirectToUserProfileFragment((String) tv_target.getText());
                    }
                }
            });
//            btn_fl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if((btn_fl.getText().toString().toUpperCase()).equals("FOLLOW")){
//                        try {
//                            ApiService.apiService.followUser(MyApp.GetUserId(), (String) tv_target.getText()).enqueue(new Callback<Boolean>() {
//                                @Override
//                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                    btn_fl.setBackgroundColor(Color.GRAY);
//                                    btn_fl.setText("Unfollow");
//                                }
//
//                                @Override
//                                public void onFailure(Call<Boolean> call, Throwable t) {
//                                    Log.d("Follow", "fali");
//                                }
//                            });
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//
//                    }else{
//                        ApiService.apiService.unFollower(MyApp.GetUserId(),(String) tv_target.getText()).enqueue(new Callback<Boolean>() {
//                            @Override
//                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                btn_fl.setBackgroundColor(Color.BLUE);
//                                btn_fl.setText("FOLLOW");
//                            }
//
//                            @Override
//                            public void onFailure(Call<Boolean> call, Throwable t) {
//
//                            }
//                        });
//
//                    }
//                }
//            });
        }
    }
}
