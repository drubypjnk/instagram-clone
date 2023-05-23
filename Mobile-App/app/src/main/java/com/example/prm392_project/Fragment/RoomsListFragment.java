package com.example.prm392_project.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_project.Adapter.RoomAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.RoomDTO;
import com.example.prm392_project.Interfaces.IApi;
import com.example.prm392_project.Model.Room;
import com.example.prm392_project.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RoomsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomsListFragment newInstance(String param1, String param2) {
        RoomsListFragment fragment = new RoomsListFragment();
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
        View v = inflater.inflate(R.layout.fragment_rooms_list, container, false);
        loadRooms(v);
        return v;
    }

    public void updateRooms(View v, List<RoomDTO> rooms){

        RoomAdapter adapter = new RoomAdapter(v.getContext(), rooms);
        RecyclerView rec = v.findViewById(R.id.rec_rooms_list);
        rec.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rec.setAdapter(adapter);
    }

    public void loadRooms(View v){
        IApi.myApi.getRooms(MyApp.GetUserId()).enqueue(new Callback<List<RoomDTO>>() {
            @Override
            public void onResponse(Call<List<RoomDTO>> call, Response<List<RoomDTO>> response) {
                if(response.isSuccessful()){
                    Log.d("call API [GET] Rooms/getRooms/{userId}", "data: " + response.body());
                    updateRooms(v, response.body());
                } else {
                    Log.d("test", "fail: " + response.code() + ", " + response.message());
                }

            }

            @Override
            public void onFailure(Call<List<RoomDTO>> call, Throwable t) {
                Log.d("test", "failed: " + t.getMessage());
            }

        });
    }
}