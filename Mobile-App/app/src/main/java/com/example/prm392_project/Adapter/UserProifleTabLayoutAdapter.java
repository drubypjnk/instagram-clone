package com.example.prm392_project.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.prm392_project.Fragment.ChangeInformationFragment;
import com.example.prm392_project.Fragment.ChangePasswordFragment;
import com.example.prm392_project.Fragment.MyProfileFragment;

public class UserProifleTabLayoutAdapter extends FragmentStatePagerAdapter
{
    int numPage=3;
    public UserProifleTabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MyProfileFragment();
            case 1:return new ChangeInformationFragment();
            case 2:return new ChangePasswordFragment();
            default:return new MyProfileFragment();
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
            case 0:return "User Information";
            case 1: return "Change information";
            case 2:return "Change Password";
        }
        return null;
    }
}
