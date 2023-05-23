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

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.PostItemAdapter;
import com.example.prm392_project.Adapter.PostItemAdapter;
import com.example.prm392_project.DTO.PostItemDTO;
import com.example.prm392_project.DTO.PostItemDTO;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostManagerFragment extends Fragment implements CallbackFragment{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id;
    CardView cardView;
    CallbackFragment callbackFragment;
    RecyclerView rcv_list;
    SearchView searchView;
    List<PostItemDTO> searchList;
    List<PostItemDTO> postItemDTOS;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostManagerFragment newInstance(String param1, String param2) {
        PostManagerFragment fragment = new PostManagerFragment();
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
        View view = inflater.inflate(R.layout.post_manager_fragment, container, false);
        // Inflate the layout for this fragment
        rcv_list = view.findViewById(R.id.rcv_list);
        searchView= view.findViewById(R.id.searchView);
        cardView = view.findViewById(R.id.card_searchview);
        getPostToManager();
        return view;
    }

    public void getPostToManager() {
        ApiService.apiService.getListPostToManage().enqueue(new Callback<List<PostItemDTO>>() {
            @Override
            public void onResponse(Call<List<PostItemDTO>> call, Response<List<PostItemDTO>> response) {
                postItemDTOS= response.body();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                rcv_list.setLayoutManager(gridLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                rcv_list.addItemDecoration(dividerItemDecoration);
                PostItemAdapter adapter = new PostItemAdapter(postItemDTOS,callbackFragment);
                rcv_list.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchList = new ArrayList<>();

                        if (query.length()>0){
                            for (PostItemDTO u: postItemDTOS) {
                                if (u.getTitle().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))||
                                        u.getUserId().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))){
                                    searchList.add(u);
                                }
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            PostItemAdapter adapter = new PostItemAdapter(searchList,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }else {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            PostItemAdapter adapter = new PostItemAdapter(postItemDTOS,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        searchList = new ArrayList<>();

                        if (newText.length()>0){
                            for (PostItemDTO u: postItemDTOS) {
                                if (u.getTitle().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))||
                                        u.getUserId().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                                    searchList.add(u);
                                }
                            }
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            PostItemAdapter adapter = new PostItemAdapter(searchList,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }else {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            rcv_list.setLayoutManager(gridLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                            rcv_list.addItemDecoration(dividerItemDecoration);
                            PostItemAdapter adapter = new PostItemAdapter(postItemDTOS,callbackFragment);
                            rcv_list.setAdapter(adapter);
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PostItemDTO>> call, Throwable t) {

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