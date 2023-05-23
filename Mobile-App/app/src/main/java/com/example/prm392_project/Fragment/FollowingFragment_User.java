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

public class FollowingFragment_User extends Fragment implements FriendlistAdapter.ButtonItemListener {
    public FollowingFragment_User(String person_id) {
        this.person_id = person_id;
    }

    private String current_user;
    private int pcurr;
    private RecyclerView rc;
    private List<FollowInforDTO> listFriend = new ArrayList<>();
    FriendlistAdapter adapter;
    private String person_id;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        current_user = sharedPreferences.getString("user_id", null);
        ApiService.apiService.getFollowingUser(person_id, current_user).enqueue(new Callback<List<FollowInforDTO>>() {
            @Override
            public void onResponse(Call<List<FollowInforDTO>> call, Response<List<FollowInforDTO>> response) {
                listFriend = response.body();
                Action(view);
            }

            @Override
            public void onFailure(Call<List<FollowInforDTO>> call, Throwable t) {
                Action(view);
            }
        });
    }

    public void Action(View view) {
        adapter = new FriendlistAdapter();
        adapter.setCurrentUser(current_user);
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
            ApiService.apiService.unfollowUser(item.getFollowId()).enqueue(new Callback<Boolean>() {
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
            ApiService.apiService.refollowUser(item.getFollowId()).enqueue(new Callback<Boolean>() {
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
        adapter.setList(listFriend);
        adapter.notifyDataSetChanged();
        rc = view.findViewById(R.id.rc_following);

    }
}
