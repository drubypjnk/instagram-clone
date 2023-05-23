package com.example.prm392_project.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.UserProifleTabLayoutAdapter;
import com.example.prm392_project.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TablayoutUserFragment extends Fragment implements View.OnClickListener  {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static TablayoutUserFragment instance;
    public static TablayoutUserFragment getInstance() {
        return instance;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        instance = this;
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        UserProifleTabLayoutAdapter adapter = new UserProifleTabLayoutAdapter(manager, 3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayoutTitleColor();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                        break;
                    case 1:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_500));
                        break;
                    case 2:
                        tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user_tab_layout, container, false);
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
            case 2:
                tabLayout.setTabTextColors(Color.BLACK, getResources().getColor(R.color.purple_200));
                break;
        }
    }
    public void reDirectToUserEdit(){
                    viewPager.setCurrentItem(1);

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
