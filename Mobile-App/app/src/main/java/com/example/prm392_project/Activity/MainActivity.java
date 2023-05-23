package com.example.prm392_project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.Fragment.ActivityFragment;
import com.example.prm392_project.Fragment.DashbroadFragment;
import com.example.prm392_project.Fragment.LoginFragment;
import com.example.prm392_project.Fragment.NewFeedFragment;
import com.example.prm392_project.Fragment.PostDetailFragment;

import com.example.prm392_project.Fragment.RoomsListFragment;
import com.example.prm392_project.Fragment.SearchFragment;
import com.example.prm392_project.Fragment.TablayouFriendList;
import com.example.prm392_project.Fragment.TablayoutFriendList_user;
import com.example.prm392_project.Fragment.TablayoutUserFragment;
import com.example.prm392_project.Fragment.UserProfileFragment;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;
import com.example.prm392_project.Service.MesageSignalrService;
import com.example.prm392_project.Service.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, CallbackFragment {
    private static MainActivity instance;
    public BottomNavigationView nv;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ActivityFragment activityFragment;
    RoomsListFragment roomsListFragment;
    SearchFragment searchFragment;
    LoginFragment loginFragment;
    SharedPreferences sharedPreferences;
    String user_id;
    public boolean isViewDetailPost = false;

    private String _target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        activityFragment = ActivityFragment.newInstance(null, null);
        searchFragment = SearchFragment.newInstance(null, null);
        NewFeedFragment newFeedFragment = new NewFeedFragment();
        loginFragment = LoginFragment.newInstance(null,null);
        //clone user

        sharedPreferences = this.getSharedPreferences("userFile",MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", null);
        MyApp.SetUserId(user_id);
        cloneAccount();
        instance = this;
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For day mode theme
        setContentView(R.layout.activity_main);
        nv = findViewById(R.id.nv_bottom);
        nv.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        activityFragment = ActivityFragment.newInstance(null, null);
        roomsListFragment = RoomsListFragment.newInstance(null, null);
        searchFragment = SearchFragment.newInstance(null, null);

        Intent intent = new Intent(this, NotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        Intent intent2 = new Intent(this, MesageSignalrService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent2);
        }

        //Get Pending intent for clicked notification
        String type = getIntent().getStringExtra("notification_type");
        if(type != null && !type.equals("")){
            switch (type) {
                case "message":
                    redirectToMessageListFragment(Integer.parseInt(getIntent().getStringExtra("targetId")));
                    break;
                case "post":
                    //start post fragment(must replace activityFragment to PostFragment)
                    fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment myFrag = new PostDetailFragment();
                    Bundle bundle = new Bundle();

                    /** Set all key and value for bundle to send to fragment */
                    bundle.putString(CommonConstant.POST_ID, getIntent().getStringExtra("targetId"));

                    /** Send data to {@link PostDetailFragment} (myFrag) */
                    myFrag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, myFrag);
                    fragmentTransaction.addToBackStack("addToBackStack");
                    fragmentTransaction.commit();
                    break;
                case "follow":
                    redirectToUserProfileFragment(getIntent().getStringExtra("targetId"));
                    break;
            }
        } else if(user_id != null && !user_id.equals(CommonConstant.EMPTY)){
            //Start new feed fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, newFeedFragment);
            fragmentTransaction.commit();
        } else{
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, loginFragment);
            fragmentTransaction.addToBackStack("fragBack");
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nv_home:
                if(isViewDetailPost){
                    fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment myFrag = new PostDetailFragment();
                    Bundle bundle = new Bundle();

                    /** Set all key and value for bundle to send to fragment */
                    bundle.putString(CommonConstant.POST_ID, _target);

                    /** Send data to {@link PostDetailFragment} (myFrag) */
                    myFrag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, myFrag);
                    fragmentTransaction.addToBackStack("Postdetail");
                    fragmentTransaction.commit();
                } else {
                    NewFeedFragment newFeedFragment = new NewFeedFragment();

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, newFeedFragment, "newFeedFragment");
                    fragmentTransaction.addToBackStack("fragBack");
                    fragmentTransaction.commit();
                }
                return true;
            case R.id.nv_search:
                fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, searchFragment, "searchFragment");
                fragmentTransaction.commit();
                return true;
            case R.id.nv_add:
                Intent intentAddPost = new Intent(MainActivity.this, AddPostStepOneActivity.class);
                MainActivity.this.startActivity(intentAddPost);

                return true;
            case R.id.nv_notification:
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, activityFragment, "activityFragment");
                fragmentTransaction.addToBackStack("fragBack");
                fragmentTransaction.commit();
                return true;
            case R.id.nv_profile:

                if (user_id != null) {
                    changeFragment(new DashbroadFragment());
                } else {
                    changeFragment(loginFragment);
                }
                return true;
        }
        return false;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void redirectToFriendListFragment(int option) {
        fragmentTransaction = fragmentManager.beginTransaction();
        TablayouFriendList tab = new TablayouFriendList(option);
        fragmentTransaction.replace(R.id.fragment_container, tab, "FriendListFragment_"+option);
        fragmentTransaction.addToBackStack("FriendListFragment_"+option);
        fragmentTransaction.commit();
    }

    public void redirectToFriend_UserListFragment(int option, String person_id) {
        fragmentTransaction = fragmentManager.beginTransaction();
        TablayoutFriendList_user tab = new TablayoutFriendList_user(option, person_id);
        fragmentTransaction.replace(R.id.fragment_container, tab, "FriendListFragment_user");
        fragmentTransaction.addToBackStack("FriendListFragment_user");
        fragmentTransaction.commit();
    }

    public void redirectToMyProfle() {
        fragmentTransaction = fragmentManager.beginTransaction();
        TablayoutUserFragment tablayoutUserFragment = new TablayoutUserFragment();
        fragmentTransaction.replace(R.id.fragment_container, tablayoutUserFragment, "myprofileFragment");
        fragmentTransaction.addToBackStack("myprofileFragment");
        fragmentTransaction.commit();
    }

    public void redirectToUserProfileFragment(String user_id) {
        if (this.user_id.equals(user_id)) {
            redirectToMyProfle();
        } else {
            UserProfileFragment fragment = new UserProfileFragment(user_id);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "UserProfileFragment");
            fragmentTransaction.addToBackStack("UserProfileFragment");
            fragmentTransaction.commit();
        }
    }


    public void redirectToMessageListFragment(int room_id){
        Intent myIntent = new Intent(this, RoomActivity.class);
        myIntent.putExtra("roomId", room_id); //Optional parameters
        startActivity(myIntent);
    }
    public void cloneAccount(){
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
//        editor.putString("user_id", "user");
        editor.putString("user_id", user_id);
        editor.putString("username", null);
        editor.commit();
    }
    public void back(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("changefragment");

        fragmentTransaction.commit();
    }

    public boolean isViewDetailPost() {
        return isViewDetailPost;
    }

    public void setViewDetailPost(boolean viewDetailPost, String postId) {
        isViewDetailPost = viewDetailPost;
        _target = postId;
    }
}