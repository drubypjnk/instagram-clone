package com.example.prm392_project.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.PostDTO;
import com.example.prm392_project.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedFragment extends Fragment {
    private List<PostDTO> listPost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String userId = sharedPreferences.getString("user_id", null);

        View view = inflater.inflate(R.layout.new_feed_fragment, container, false);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        ScrollView scrollView = view.findViewById(R.id.scroll_feed);
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setId(12323);
        ((ImageView)view.findViewById(R.id.imv_btn_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = fragMan.beginTransaction();
                RoomsListFragment fragment = new RoomsListFragment();
                transaction.replace(R.id.fragment_container, fragment, "activityFragment");
                transaction.addToBackStack("fragBack");
                transaction.commit();
            }
        });

        /** Get all post for newsfeed from API by current login user */
        /** Get post of user and who has been followed */
        ApiService.apiService.getListPost(userId).enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                listPost = response.body();
                MyApp.postDTOList = listPost;
                int index = 0;
                for (PostDTO post: listPost) {
                    Fragment myFrag = new PostFragment();
                    Bundle bundle = new Bundle();

                    /** Add fragment to container */
                    fragTransaction.add(linearLayout.getId(), myFrag);

                    /** Set all key and value for bundle to send to fragment */
//                    bundle.putString(CommonConstant.IS_LIKED, post.getLiked().toString());
//                    bundle.putString(CommonConstant.POST_ID, post.getPostId().toString());
//                    bundle.putString(CommonConstant.TOTAL_LIKED, post.getTotalLike().toString());
//                    bundle.putString(CommonConstant.POST_IMAGE, post.getImage());
//                    bundle.putString(CommonConstant.AUTHOR, post.getUsername());
//                    bundle.putString(CommonConstant.LOCATION, post.getLocation());
//                    bundle.putString(CommonConstant.CONTENT, post.getContent());
//                    bundle.putString(CommonConstant.AVATAR, post.getAvatar());
//                    bundle.putString(CommonConstant.USER_ID, post.getUserId());

                    bundle.putInt("index", index);
                    index++;
                    /** Send data to PostFragment (myFrag) */
                    myFrag.setArguments(bundle);
                }
                fragTransaction.commit();

                /** Add layout container to scroll view */
                scrollView.addView(linearLayout);
            }
            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Log.d("error", "callapierror");
            }
        });
        return view;
    }
}
