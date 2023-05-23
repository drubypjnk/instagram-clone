package com.example.prm392_project.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.prm392_project.Fragment.FollowerFragment;
import com.example.prm392_project.Fragment.FollowingFragment;

public class FriendListTabLayoutAdapter extends FragmentStatePagerAdapter
{
    int numPage=2;
    public FriendListTabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new FollowingFragment();
            case 1:return new FollowerFragment();

            default:return new FollowerFragment();
        }
    }


    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Following";
            case 1: return "Follower";
        }
        return null;
    }

}
