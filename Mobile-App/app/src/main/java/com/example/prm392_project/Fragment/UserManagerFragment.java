package com.example.prm392_project.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.UserItemAdapter;
import com.example.prm392_project.DTO.UserToManageDTO;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.Model.UserContainer;
import com.example.prm392_project.Model.UserItem;
import com.example.prm392_project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserManagerFragment extends Fragment implements CallbackFragment{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;
    CardView cardView;
    CallbackFragment callbackFragment;
    RecyclerView rcv_list;
    SearchView searchView;
    List<UserToManageDTO> searchList;
    List<UserToManageDTO> userToManageDTOS;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserManagerFragment newInstance(String param1, String param2) {
        UserManagerFragment fragment = new UserManagerFragment();
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
        user_id = sharedPreferences.getString("user_id", null);
        fragmentManager = getActivity().getSupportFragmentManager();
        callbackFragment = this::changeFragment;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_manager, container, false);
        // Inflate the layout for this fragment
        rcv_list = view.findViewById(R.id.rcv_list);
        searchView= view.findViewById(R.id.searchView);
        cardView = view.findViewById(R.id.card_searchview);
        getUserToManager();
        return view;
    }
    public void getUserToManager(){
        ApiService.apiService.getUserToManage(user_id).enqueue(new Callback<List<UserToManageDTO>>() {
            @Override
            public void onResponse(Call<List<UserToManageDTO>> call, Response<List<UserToManageDTO>> response) {
                userToManageDTOS= response.body();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                rcv_list.setLayoutManager(gridLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                rcv_list.addItemDecoration(dividerItemDecoration);
                UserItemAdapter adapter = new UserItemAdapter(userToManageDTOS,callbackFragment);
                rcv_list.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchList = new ArrayList<>();

                        if (query.length()>0){
                            for (UserToManageDTO u: userToManageDTOS) {
                                if (u.fullName.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))||
                                        u.email.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))){
                                    searchList.add(u);
                                }
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            UserItemAdapter adapter = new UserItemAdapter(searchList,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }else {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            UserItemAdapter adapter = new UserItemAdapter(userToManageDTOS,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        searchList = new ArrayList<>();

                        if (newText.length()>0){
                            for (UserToManageDTO u: userToManageDTOS) {
                                if (u.fullName.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))||
                                        u.email.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                                    searchList.add(u);
                                }
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            UserItemAdapter adapter = new UserItemAdapter(searchList,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }else {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            UserItemAdapter adapter = new UserItemAdapter(userToManageDTOS,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UserToManageDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("fragBack");
        fragmentTransaction.commit();
    }
}