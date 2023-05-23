package com.example.prm392_project.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.RecentSearchAdapter;
import com.example.prm392_project.Adapter.SearchUserAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DAO.SearchKeyDAO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.Database.InstaDatabase;
import com.example.prm392_project.Entity.SearchKey;
import com.example.prm392_project.Model.User;
import com.example.prm392_project.R;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SearchView searchView;
    private boolean searched = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView)view.findViewById(R.id.sv_people);
        ProgressBar pb_search = view.findViewById(R.id.pb_search_people);
        pb_search.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.rec_search_result);
        RecyclerView rec_recent = view.findViewById(R.id.rec_recent_search);
        TextView tv_rc = view.findViewById(R.id.tv_recent);
        if(MyApp.recentSearch == null){
            MyApp.recentSearch = InstaDatabase.getInstance(getContext()).searchKeyDAO().getAll();
        }
        if(!searched){
            if(MyApp.recentSearch.size() > 0){
                RecentSearchAdapter recentSearchAdapter = new RecentSearchAdapter(MyApp.recentSearch, this);
                rec_recent.setLayoutManager(new LinearLayoutManager(getContext()));
                rec_recent.setAdapter(recentSearchAdapter);
            } else {
                rec_recent.setVisibility(View.INVISIBLE);
                tv_rc.setVisibility(View.GONE);
            }
            recyclerView.setVisibility(View.GONE);
        } else {
            pb_search.setVisibility(View.INVISIBLE);
            rec_recent.setVisibility(View.INVISIBLE);
            tv_rc.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }


        if(MyApp.userInforDTOS != null){
            SearchUserAdapter searchUserAdapter = new SearchUserAdapter(MyApp.userInforDTOS);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(searchUserAdapter);
        }
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                pb_search.setVisibility(View.INVISIBLE);
                rec_recent.setVisibility(View.VISIBLE);
                tv_rc.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                searchView.setIconified(false);
                searchView.requestFocus();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rec_recent.setVisibility(View.INVISIBLE);
                tv_rc.setVisibility(View.INVISIBLE);
                pb_search.setVisibility(View.VISIBLE);
                InstaDatabase db = InstaDatabase.getInstance(getContext());
                SearchKeyDAO dao = db.searchKeyDAO();
                dao.insertAll(new SearchKey(query, new Date()));
                MyApp.recentSearch = dao.getAll();
                ApiService.apiService.searchUser(query).enqueue(new Callback<List<UserInforDTO>>() {
                    @Override
                    public void onResponse(Call<List<UserInforDTO>> call, Response<List<UserInforDTO>> response) {
                        searched = true;
                        pb_search.setVisibility(View.INVISIBLE);
                        rec_recent.setVisibility(View.INVISIBLE);
                        tv_rc.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        List<UserInforDTO> userInforDTOS = response.body();
                        MyApp.userInforDTOS = userInforDTOS;
                        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(userInforDTOS);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(searchUserAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<UserInforDTO>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    rec_recent.setVisibility(View.VISIBLE);
                    rec_recent.setLayoutManager(new LinearLayoutManager(getContext()));
                    rec_recent.setAdapter(new RecentSearchAdapter(MyApp.recentSearch, SearchFragment.this));
                    tv_rc.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }
    public void RecentSearchQuery(String query){
        searchView.setQuery(query, true);
    }
}