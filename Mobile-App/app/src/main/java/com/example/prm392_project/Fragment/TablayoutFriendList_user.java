package com.example.prm392_project.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.FriendListTabLayoutAdapter;
import com.example.prm392_project.Adapter.FriendList_UserTabLayoutAdapter;
import com.example.prm392_project.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TablayoutFriendList_user extends Fragment implements View.OnClickListener {
    String person_id;
    public TablayoutFriendList_user() {

    }
    public static int count=0;


    public TablayoutFriendList_user(int option,String person_id) {
        this.person_id=person_id;
        this.option = option;
    }

    private int option;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    FriendList_UserTabLayoutAdapter adapter;
    ImageView imgBack;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager_listfriend);
        tabLayout = view.findViewById(R.id.tabLayout_listfriend);

        imgBack=view.findViewById(R.id.backToProfile_friendlist);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MainActivity.getInstance().redirectToUserProfileFragment(person_id);
            }
        });

        FragmentManager manager = getActivity().getSupportFragmentManager();
        this.adapter = new FriendList_UserTabLayoutAdapter(manager, 2,person_id);
        viewPager.setAdapter(this.adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayoutTitleColor();
        //get option 0:following 1:follower
        TabLayout.Tab tab = tabLayout.getTabAt(option);
        tab.select();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                        if(count==0){
                            count++;
                        }else{
                            MainActivity.getInstance().redirectToFriend_UserListFragment(tab.getPosition(),person_id);
                        }
                        break;
                    case 1:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_500));
                        if(count==0){
                            count++;
                        }else{
                            MainActivity.getInstance().redirectToFriend_UserListFragment(tab.getPosition(),person_id);
                        }
                        break;
//                    case 2:
//                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
//                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//                this.adapter.getItem(option);
    }

    public void getFollower() {
        this.adapter.getItem(1);
    }

    public void getFollowing() {
        this.adapter.getItem(0);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.friend_list_tab_layout, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }

    private void setTabLayoutTitleColor() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.teal_700));
                break;
            case 1:
                tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.teal_700));
                break;
//            case 2:
//                tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
//                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            super.onBackPressed();
//        } else {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }
}
