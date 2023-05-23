package com.example.prm392_project.Fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.ImagePostAdapter;
import com.example.prm392_project.Application.MyViewModel;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.PostInforDTO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileFragment extends Fragment implements ImagePostAdapter.ItemListener {

    ImagePostAdapter ImagePostAdapter;
    private int pcurr;
    //    private List<PostImg> listPostImgs = new ArrayList();
    private List<PostInforDTO> listPostImgs = new ArrayList<>();
    private RecyclerView rc;
    private TextView tvEdit, tvFollower, tvFollowing, tvDesc, tvNumPost,tvFullName;
    ImagePostAdapter adapter;
    private UserInforDTO userInforDTO;
    ImageView imageAvatar;
    ProgressBar pb;
    private MyViewModel viewModel;

    public MyProfileFragment() {

    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        // Lưu trữ dữ liệu vào ViewModel
        viewModel.setData("my_profile");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb=view.findViewById(R.id.progressBar_userProfile);
        pb.setVisibility(ProgressBar.VISIBLE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String user_id = sharedPreferences.getString("user_id", null);
        ApiService.apiService.getUserByID(user_id).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();
                if (userInforDTO != null) {
                    listPostImgs = userInforDTO.getListPost();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                }
                Action(view);
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {
                Action(view);
            }
        });

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }


    public void Action(View view) {
        adapter = new ImagePostAdapter();
        initView(view);
        rc = (RecyclerView) view.findViewById(R.id.rcPostImage);
        adapter.setItemListener(this);
        int numberOfColumns = 3;
        rc.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        rc.setAdapter(adapter);


        //Event handle
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //following
        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().redirectToFriendListFragment(0);
            }
        });
        //follower
        tvFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().redirectToFriendListFragment(1);
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TablayoutUserFragment.getInstance().reDirectToUserEdit();
            }
        });
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
        fragTransaction.addToBackStack("Myprofile_Postdetail_Backstack");
        fragTransaction.commit();
    }


    public void initView(View view) {
        adapter.setList(listPostImgs);
        adapter.notifyDataSetChanged();
        rc = view.findViewById(R.id.rcPostImage);
        tvEdit = view.findViewById(R.id.tvEditProfile);
        tvFollower = view.findViewById(R.id.tvFollower);
        tvFollowing = view.findViewById(R.id.tvFlowing);
        tvNumPost = view.findViewById(R.id.tvNumPost);
        tvDesc = view.findViewById(R.id.tvDesc);
        imageAvatar=view.findViewById(R.id.profile_image);
        tvFullName=view.findViewById(R.id.tvFullName);
        setUpField();
    }

    public void setUpField() {
        if (userInforDTO != null) {
            tvNumPost.setText(userInforDTO.getListPost().size() + "");
            tvFollower.setText(userInforDTO.getListFollower().size() + "");
            tvFollowing.setText(userInforDTO.getListFollowing().size() + "");
            tvDesc.setText(userInforDTO.getDesription());
            imageAvatar.setImageBitmap(getImageBase64(userInforDTO.getAvartarImage().getContent()));
            tvFullName.setText(userInforDTO.getFullName());
        }
    }
    public Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}