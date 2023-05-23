package com.example.prm392_project.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.UserLoginDTO;
import com.example.prm392_project.DTO.UserPassDTO;
import com.example.prm392_project.R;

import com.example.prm392_project.Interfaces.CallbackFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements CallbackFragment {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnRegister, btnForgot;
    CallbackFragment callbackFragment;
    String username, password;
    UserLoginDTO user = new UserLoginDTO();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        String userName = sharedPreferences.getString("username",null);
        edtUsername = view.findViewById(R.id.edt_username);
        edtPassword = view.findViewById(R.id.edt_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.link_register);
        btnForgot = view.findViewById(R.id.link_forgot);

        if (userName!=null){
            edtUsername.setText(userName);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                if(!username.isEmpty()&&!password.isEmpty()){
                    onInit();
                }else {
                    Toast.makeText(getContext(), "Some Input blank!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new RegisterFragment());
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ForgotPasswordFragment());
            }
        });
        // Inflate the layout for this fragment
        return view;

    }

    public void onInit() {
        //get user
        ApiService.apiService.getUserLogin(username, password).enqueue(new Callback<UserLoginDTO>() {
            @Override
            public void onResponse(Call<UserLoginDTO> call, Response<UserLoginDTO> response) {
                user = response.body();
                if (user != null) {
                    if (user.status==1){
                        editor.putString("user_id", user.getUser_id());
                        editor.putString("username", null);
                        editor.apply();
                        MyApp.SetUserId(user.getUser_id());
                        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(myIntent);
                    }else {
                        Toast.makeText(getContext(), "Account be deactivated! Try later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Call error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setCallbackFragment(CallbackFragment callbackFragment) {
        this.callbackFragment = callbackFragment;
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