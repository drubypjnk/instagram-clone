package com.example.prm392_project.Fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.ImagePostAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.FollowerInforDTO;
import com.example.prm392_project.DTO.PostInforDTO;
import com.example.prm392_project.DTO.RoomDTO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.Interfaces.IApi;
import com.example.prm392_project.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment implements ImagePostAdapter.ItemListener {

    ImagePostAdapter ImagePostAdapter;
    private int pcurr;
    private String person_id;
    //    private List<PostImg> listPostImgs = new ArrayList();
    private List<PostInforDTO> listPostImgs = new ArrayList<>();
    private RecyclerView rc;
    private TextView tvFollower, tvName, tvUserName, tvFollowing, tvDesc, tvNumPost,tvFullName;
    private Button btFollow;
    ImageView imageAvatar;
    ImagePostAdapter adapter;
    private UserInforDTO userInforDTO;
    ProgressBar pb;
    ImageView img_back;
    public UserProfileFragment() {

    }

    public UserProfileFragment(String user_id) {
        this.person_id = user_id;
    }
    public String getSecondLastTagFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        int index = fragmentManager.getBackStackEntryCount()-1;
        FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index-1);
        String tag = backEntry.getName();
        return tag;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_back=view.findViewById(R.id.backToProfile_userProfile);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String secondLastTagFragment = getSecondLastTagFragment();
                if(secondLastTagFragment.equalsIgnoreCase("FriendListFragment_1")){
                    MainActivity.getInstance().redirectToFriendListFragment(1);
                }else if(secondLastTagFragment.equalsIgnoreCase("FriendListFragment_0")){
                    MainActivity.getInstance().redirectToFriendListFragment(0);
                }
                else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            pb=view.findViewById(R.id.progressBar_userProfile1);
            pb.setVisibility(ProgressBar.VISIBLE);
          ApiService.apiService.getUserByID(this.person_id).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();
                if (userInforDTO != null) {
                    listPostImgs = userInforDTO.getListPost();
                }
                Action(view);
                    pb.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {
                Action(view);
            }
        });
        ((Button)view.findViewById(R.id.btMessage_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IApi.myApi.getRoomByUsers(MyApp.GetUserId(), person_id).enqueue(new Callback<RoomDTO>() {
                    @Override
                    public void onResponse(Call<RoomDTO> call, Response<RoomDTO> response) {
                        if(response.isSuccessful()){
                            Log.d("call API [GET] Rooms/getRoomByUsers/{userId}", "data: " + response.body());
                            MainActivity.getInstance().redirectToMessageListFragment(response.body().getRoom().getRoomId());
                        } else {
                            Log.d("test", "fail: " + response.code() + ", " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<RoomDTO> call, Throwable t) {
                        Log.d("test", "failed: " + t.getMessage());
                    }

                });
            }
        });

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }


    public void Action(View view) {
        adapter = new ImagePostAdapter();
        initView(view);
        rc = (RecyclerView) view.findViewById(R.id.rcPostImage_user);
        adapter.setItemListener(this);
        int numberOfColumns = 3;
        rc.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        rc.setAdapter(adapter);


        //following
        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().redirectToFriend_UserListFragment(0, person_id);
            }
        });
        //follower
        tvFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().redirectToFriend_UserListFragment(1, person_id);
            }
        });

        //follow
        btFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                String user_id = sharedPreferences.getString("user_id", null);
                if ((btFollow.getText().toString().toUpperCase()).equals("FOLLOW")) {
                    try {
                        ApiService.apiService.followUser(user_id, userInforDTO.userId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                btFollow.setBackgroundColor(Color.GRAY);
                                btFollow.setText("UNFOLLOW");
                                int num = Integer.parseInt(tvFollower.getText().toString());
                                tvFollower.setText(num+1 + "");
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    ApiService.apiService.unFollower(userInforDTO.userId, user_id).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            btFollow.setBackgroundColor(Color.BLUE);
                            btFollow.setText("FOLLOW");
                            int num = Integer.parseInt(tvFollower.getText().toString());
                            tvFollower.setText( num-1+"");
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });

                }
            }
        });


    }


    public void initView(View view) {
        adapter.setList(listPostImgs);
        adapter.notifyDataSetChanged();
        rc = view.findViewById(R.id.rcPostImage_user);
        tvFollower = view.findViewById(R.id.tvFollower_user);
        tvFollowing = view.findViewById(R.id.tvFlowing_user);
        tvNumPost = view.findViewById(R.id.tvNumPost_user);
        tvDesc = view.findViewById(R.id.tvDesc_user);
        tvName = view.findViewById(R.id.tvName_user);
        tvUserName = view.findViewById(R.id.tv_UserName_user);
        btFollow = view.findViewById(R.id.btFollow_user);
        imageAvatar = view.findViewById(R.id.profile_image_user);
        btFollow = view.findViewById(R.id.btFollow_user);
        tvFullName=view.findViewById(R.id.tvFullName_user);
        setUpField();
    }

    public void setUpField() {
        if (userInforDTO != null) {
            tvNumPost.setText(userInforDTO.getListPost().size() + "");
            tvFollower.setText(userInforDTO.getListFollower().size() + "");
            tvFollowing.setText(userInforDTO.getListFollowing().size() + "");
            tvDesc.setText(userInforDTO.getDesription());
            tvName.setText(userInforDTO.getUsername());
            tvUserName.setText(userInforDTO.getUserId());
            tvFullName.setText(userInforDTO.getFullName());
            imageAvatar.setImageBitmap(getImageBase64(userInforDTO.getAvartarImage().getContent()));
            //check follow
            List<FollowerInforDTO> listFollower = userInforDTO.getListFollower();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            boolean check=false;
            String user_id = sharedPreferences.getString("user_id", null);
            for (FollowerInforDTO f:listFollower) {
                if(f.getUserId().equalsIgnoreCase(user_id)){
                    check=true;
                }
            }
            if(check==true){
                btFollow.setBackgroundColor(Color.GRAY);
                btFollow.setText("UNFOLLOW");
            }else {
                btFollow.setBackgroundColor(Color.BLUE);
                btFollow.setText("FOLLOW");
            }
        }
    }

    public Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public void onItemclick(View view, int position) {
        Integer postId = listPostImgs.get(position).getPostId();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        Fragment myFrag = new PostDetailFragment();
        Bundle bundle = new Bundle();

        /** Set all key and value for bundle to send to fragment */
        bundle.putString(CommonConstant.POST_ID, postId.toString());

        /** Send data to {@link PostDetailFragment} (myFrag) */
        myFrag.setArguments(bundle);
        fragTransaction.replace(R.id.fragment_container, myFrag);
        fragTransaction.addToBackStack("addToBackStack");
        fragTransaction.commit();
    }

}