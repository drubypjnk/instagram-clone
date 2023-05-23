package com.example.prm392_project.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Adapter.ActivityContainerAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Model.ActivityContainer;
import com.example.prm392_project.Model.ActivityItem;
import com.example.prm392_project.R;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    private ApiService myApiService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance(String param1, String param2) {
        ActivityFragment fragment = new ActivityFragment();
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
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imv = (ImageView)view.findViewById(R.id.imv_back);
        imv.setImageResource(R.drawable.baseline_arrow_back_24);
        RecyclerView rec_activity_container = (RecyclerView) view.findViewById(R.id.rec_activity_container);
        ProgressBar progressBar = view.findViewById(R.id.pb_activity);
        progressBar.setVisibility(View.VISIBLE);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        if(MyApp.activityContainers == null){
            myApiService = ApiService.apiService;

            myApiService.getActivities(MyApp.GetUserId()).enqueue(new Callback<List<ActivityContainer>>() {
                @Override
                public void onResponse(Call<List<ActivityContainer>> call, Response<List<ActivityContainer>> response) {
                    Log.d("quang", "success");
                    MyApp.activityContainers = response.body();
                    ActivityContainerAdapter adapter = new ActivityContainerAdapter(response.body(), getContext());
                    rec_activity_container.setLayoutManager(new LinearLayoutManager(getContext()));
                    rec_activity_container.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<List<ActivityContainer>> call, Throwable t) {
                    Log.d("quang","fail");
                }
            });
        } else {
            ActivityContainerAdapter adapter = new ActivityContainerAdapter(MyApp.activityContainers, getContext());
            rec_activity_container.setLayoutManager(new LinearLayoutManager(getContext()));
            rec_activity_container.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}