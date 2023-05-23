package com.example.prm392_project.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.FriendListTabLayoutAdapter;
import com.example.prm392_project.Adapter.UserProifleTabLayoutAdapter;
import com.example.prm392_project.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TablayouFriendList extends Fragment implements View.OnClickListener {
    public TablayouFriendList() {

    }
    public static int count=0;

    public TablayouFriendList(int option) {
        this.option = option;
    }

    private int option;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ImageView imgBack;
    FriendListTabLayoutAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager_listfriend);
        tabLayout = view.findViewById(R.id.tabLayout_listfriend);
        imgBack=view.findViewById(R.id.backToProfile_friendlist);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().redirectToMyProfle();
            }
        });
        FragmentManager manager = getActivity().getSupportFragmentManager();
        this.adapter = new FriendListTabLayoutAdapter(manager, 2);
        viewPager.setAdapter(this.adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
                            if(count==0){
                                count++;
                            }else{
                                MainActivity.getInstance().redirectToFriendListFragment(tab.getPosition());
                            }
                        break;
                    case 1:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_500));
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                        if(count==0){
                            count++;
                        }else{
                            MainActivity.getInstance().redirectToFriendListFragment(tab.getPosition());
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
