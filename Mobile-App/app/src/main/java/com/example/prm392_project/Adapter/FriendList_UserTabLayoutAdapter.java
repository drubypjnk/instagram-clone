package com.example.prm392_project.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.prm392_project.Fragment.FollowerFragment;
import com.example.prm392_project.Fragment.Follower_UserFragment;
import com.example.prm392_project.Fragment.FollowingFragment;
import com.example.prm392_project.Fragment.FollowingFragment_User;

public class FriendList_UserTabLayoutAdapter extends FragmentStatePagerAdapter
{
    String person_id;
    int numPage=2;

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public FriendList_UserTabLayoutAdapter(@NonNull FragmentManager fm, int behavior,String person_id) {
        super(fm, behavior);
        this.person_id=person_id;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new FollowingFragment_User(person_id);
            case 1:return new Follower_UserFragment(person_id);
            default:return new Follower_UserFragment(person_id);
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
