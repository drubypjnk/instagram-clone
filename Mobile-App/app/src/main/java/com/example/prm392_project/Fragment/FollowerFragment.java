package com.example.prm392_project.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.FriendlistAdapter;
import com.example.prm392_project.DTO.FollowInforDTO;
import com.example.prm392_project.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerFragment extends Fragment implements FriendlistAdapter.ButtonItemListener {
    private int pcurr;
    private RecyclerView rc;
    private List<FollowInforDTO> listFollower = new ArrayList<>();
    private List<FollowInforDTO> listFollowing = new ArrayList<>();
    FriendlistAdapter adapter;

    public FollowerFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String user_id = sharedPreferences.getString("user_id", null);
        //get follower
        ApiService.apiService.getFollowerByUserId(user_id).enqueue(new Callback<List<FollowInforDTO>>() {
            @Override
            public void onResponse(Call<List<FollowInforDTO>> call, Response<List<FollowInforDTO>> response) {
                List<FollowInforDTO>listFollower_tmp = response.body();
                ApiService.apiService.getFollowingByUserId(user_id).enqueue(new Callback<List<FollowInforDTO>>() {
                    //get following
                    @Override
                    public void onResponse(Call<List<FollowInforDTO>> call, Response<List<FollowInforDTO>> response) {
                        listFollowing = response.body();
                        for(FollowInforDTO fs:listFollower_tmp){
                            int count=0;
                            for (FollowInforDTO f:listFollowing) {
                                if(fs.getUserId().equalsIgnoreCase(f.getUserId())){
                                    count++;
                                }else{
                                    fs.setFlag(false);
                                }
                            }
                            if(count!=0){
                                fs.setFlag(true);
                            }
                                listFollower.add(fs);
                        }
                        //set action
                        Action(view);
                    }

                    @Override
                    public void onFailure(Call<List<FollowInforDTO>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<FollowInforDTO>> call, Throwable t) {
                Action(view);
            }
        });
    }

    public void Action(View view) {
        adapter = new FriendlistAdapter();
        initView(view);
        rc = (RecyclerView) view.findViewById(R.id.rc_following);
        adapter.setItemListener(this);
        int numberOfColumns = 1;
        rc.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        rc.setAdapter(adapter);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.following_fragment, container, false);
    }


    @Override
    public void onButtonClick(View view, int position, Button btUnfollow) {
        pcurr = position;
        String text = btUnfollow.getText().toString();
        FollowInforDTO item = adapter.getItem(pcurr);
        if (text.equalsIgnoreCase("Unfollow")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            String user_id = sharedPreferences.getString("user_id", null);
            ApiService.apiService.unFollower(item.getUserId(), user_id).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean check = response.body();
                    if (check) {
                        btUnfollow.setBackgroundColor(Color.BLUE);
                        btUnfollow.setText("Follow");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            String user_id = sharedPreferences.getString("user_id", null);
            ApiService.apiService.followUser(user_id,item.getUserId()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body()) {
                        btUnfollow.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                        btUnfollow.setText("Unfollow");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
    }



    public void initView(View view) {
        adapter.setList(listFollower);
        adapter.notifyDataSetChanged();
        rc = view.findViewById(R.id.rc_following);

    }
}
