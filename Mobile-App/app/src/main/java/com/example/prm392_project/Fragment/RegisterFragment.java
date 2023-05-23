package com.example.prm392_project.Fragment;

import android.content.Context;
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
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.DTO.UserPassDTO;
import com.example.prm392_project.DTO.UserRegisterDTO;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements CallbackFragment {
    EditText edtUsername, edtPassword, edtEmail, edtRePassword;
    Button btnRegister;
    String username, email, password, repassword;
    UserRegisterDTO user;
    UserInforDTO userInforDTO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String mess;

    Fragment fragment = new LoginFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edtUsername = view.findViewById(R.id.edt_username);
        edtPassword = view.findViewById(R.id.edt_password);
        edtEmail = view.findViewById(R.id.edt_email);
        edtRePassword = view.findViewById(R.id.edt_repassword);
        btnRegister = view.findViewById(R.id.btn_register);
        userInforDTO = new UserInforDTO();
        mess = "";

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                repassword = edtRePassword.getText().toString();

                if (!username.equals("") && !email.equals("") && !password.equals("") && !repassword.equals("")) {

                    if (!username.matches("^[a-zA-Z0-9._-]{3,20}$")) {
                        mess += "Username not correct format : Aa-Zz0-9._- ";
                        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                        mess="";
                    } else {
                        checkUsername();
                    }

                } else {
                    Toast.makeText(getContext(), "Some Input is blank!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void checkUsername() {
        ApiService.apiService.getUserByUsername(username).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();
                if (userInforDTO != null) {
                    if (mess == "") {
                        mess += "Username already used!";
                    } else {
                        mess += "\nUsername already used!";
                    }
                    Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                    mess="";
                } else {
                    if (!email.contains("@")) {
                        if (mess == "") {
                            mess += "Email not have '@'";
                        } else {
                            mess += "\nEmail not have '@'";
                        }
                        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                        mess="";
                    } else {
                        checkEmail();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {

            }
        });
    }

    public void checkEmail() {
        ApiService.apiService.getUserByEmail(email).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();
                if (userInforDTO != null) {
                    if (mess == "") {
                        mess += "Email already used!";
                    } else {
                        mess += "\nEmail already used!";
                    }
                    Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                    mess="";
                } else {
                    if (!repassword.equals(password)) {
                        if (mess == "") {
                            mess += "Password and RePassword not similar";
                        } else {
                            mess += "\nPassword and RePassword not similar";
                        }
                        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                        mess="";
                    }
                    if (mess == "") {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                        register(new UserRegisterDTO(username,email,password));

                    } else {
                        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {

            }
        });
    }
    public void register(UserRegisterDTO userRegisterDTO){
        ApiService.apiService.register(userRegisterDTO).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean isRegisted = response.body();
                if (isRegisted==true){
                    Toast.makeText(getContext(), "Registered", Toast.LENGTH_SHORT).show();
                    changeFragment(fragment);
                }else {
                    Toast.makeText(getContext(), "Registered Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

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