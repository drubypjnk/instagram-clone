package com.example.prm392_project.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.DashbroadItemAdapter;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.Model.DashbroadItem;
import com.example.prm392_project.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashbroadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashbroadFragment extends Fragment implements CallbackFragment {
    private CallbackFragment callbackFragment = this::changeFragment;
    private RecyclerView recyclerView;
    private ImageView userAvatar;
    private TextView userName;
    private Button btnDetails;

    UserInforDTO userInforDTO;
    String user_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashbroadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashbroadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashbroadFragment newInstance(String param1, String param2) {
        DashbroadFragment fragment = new DashbroadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("user_id", null);
        fragmentManager = getActivity().getSupportFragmentManager();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashbroad_fragment, container, false);
        btnDetails = view.findViewById(R.id.btn_profile);
        userAvatar = view.findViewById(R.id.imv_avatar);
        userName = view.findViewById(R.id.tv_uname);
        recyclerView = view.findViewById(R.id.rcv_dbr_item);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        setCallbackFragment(callbackFragment);
        DashbroadItemAdapter adapter = new DashbroadItemAdapter(getListDashbroadItem(), callbackFragment);
        recyclerView.setAdapter(adapter);


        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                TablayoutUserFragment tablayoutUserFragment = new TablayoutUserFragment();
                fragmentTransaction.replace(R.id.fragment_container, tablayoutUserFragment, "myprofileFragment");
                fragmentTransaction.addToBackStack("myprofileFragment");
                fragmentTransaction.commit();
            }
        });

        getUser(user_id, view);

        // Inflate the layout for this fragment
        return view;
    }

    private List<DashbroadItem> getListDashbroadItem() {
        List<DashbroadItem> items = new ArrayList<>();
            items.add(new DashbroadItem(R.drawable.ic_logout, "Logout", 3));
        if(user_id.equals("user6")){
        items.add(new DashbroadItem(R.drawable.ic_user_manage, "User manage", 1));
            items.add(new DashbroadItem(R.drawable.ic_post, "Post manage", 2));
        }

        return items;
    }








    private void getUser(String user_id, View view) {
        ApiService.apiService.getUserByID(user_id).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();

                onInit(view);
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {
                userInforDTO = null;
                onInit(view);
            }
        });

    }

    private void onInit(View view) {
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = fragmentManager.beginTransaction();
                TablayoutUserFragment tablayoutUserFragment = new TablayoutUserFragment();
                fragmentTransaction.replace(R.id.fragment_container, tablayoutUserFragment, "myprofileFragment");
                fragmentTransaction.addToBackStack("myprofileFragment");
                fragmentTransaction.commit();
            }
        });
        userName.setText(userInforDTO.username);
        userAvatar.setImageBitmap(getImageBase64(userInforDTO.getAvartarImage().getContent()));
    }
    public Bitmap getImageBase64(String base64String) {
// Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

// Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    public void setCallbackFragment(CallbackFragment callbackFragment) {
        this.callbackFragment = callbackFragment;
    }
    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("changeFragment");
        fragmentTransaction.commit();
    }
}