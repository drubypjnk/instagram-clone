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
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment implements CallbackFragment {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    EditText edt_uName, edt_uEmail;
    Button btn_send;
    String username, email;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        edt_uName = view.findViewById(R.id.edt_username_forgot);
        edt_uEmail = view.findViewById(R.id.edt_email_forgot);

        btn_send = view.findViewById(R.id.btn_sentcode);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edt_uName.getText().toString();
                email = edt_uEmail.getText().toString();
                checkUser();
            }
        });
        return view;
    }

    public void sendNewPass(String email, String newPass) {
        ApiService.apiService.sendNewPass(email, newPass).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean isSent = response.body();
                if (isSent == true) {
                    changeFragment(fragment);
                    Toast.makeText(getContext(), "New password sent to your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Some error when reset yourr pass", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void checkUser() {
        ApiService.apiService.getUserByUsernameAndEmail(username, email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean isExist = response.body();
                if (isExist == true) {
                    int length = 10; // Change this to the desired length of the random string
                    String newPass = generateRandomString(length);
                    sendNewPass(email, newPass);
                    Toast.makeText(getContext(), "Sending reset password to your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
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