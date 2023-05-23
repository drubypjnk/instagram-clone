package com.example.prm392_project.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.DTO.UserPassDTO;
import com.example.prm392_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {
    EditText edtCurPass, edtNewPass, edtConfirmPass;
    Button btSubmit;
    TextView tvMess;
    UserPassDTO user;

    public ChangePasswordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        onInit(view);

    }

    private void action() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCurPass.getText().toString().equals(user.getPassword().toString())) {
                    if (edtNewPass.getText().toString().equals(edtConfirmPass.getText().toString())) {
                        UserPassDTO newUser = user;
                        newUser.setPassword(edtNewPass.getText().toString());
                        save(newUser);
                    } else {
                        tvMess.setVisibility(TextView.VISIBLE);
                        tvMess.setText("Confirm Password wrong!!");
                        tvMess.setTextColor(getResources().getColor(R.color.red));
                    }
                } else {
                    tvMess.setVisibility(TextView.VISIBLE);
                    tvMess.setText("current password wrong !!");
                    tvMess.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }

    private void setUpBottom(View view, UserPassDTO userPassDTO) {
        edtCurPass = view.findViewById(R.id.edt_curPassword);
        edtNewPass = view.findViewById(R.id.edt_newPassword);
        edtConfirmPass = view.findViewById(R.id.edt_confirmPassword);
        btSubmit = view.findViewById(R.id.btSavePassWord);
        tvMess = view.findViewById(R.id.tvChangPassMessage);
        tvMess.setVisibility(TextView.INVISIBLE);
        user = userPassDTO;
        action();
    }

    public void save(UserPassDTO newUser) {
        ApiService.apiService.changePassword(newUser).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                tvMess.setVisibility(TextView.VISIBLE);
                tvMess.setText("Save successfully !!");
                tvMess.setTextColor(getResources().getColor(R.color.green));
                user = newUser;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                tvMess.setVisibility(TextView.VISIBLE);
                tvMess.setText("Save failed!!");
                tvMess.setTextColor(getResources().getColor(R.color.red));
            }
        });
    }

    public void onInit(View view) {
        //get user
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String user_id = sharedPreferences.getString("user_id", null);

        final UserPassDTO[] userList = {new UserPassDTO()};
        ApiService.apiService.checkUser(user_id).enqueue(new Callback<UserPassDTO>() {
            @Override
            public void onResponse(Call<UserPassDTO> call, Response<UserPassDTO> response) {
                UserPassDTO body = response.body();
                userList[0] = body;
                setUpBottom(view, userList[0]);
            }

            @Override
            public void onFailure(Call<UserPassDTO> call, Throwable t) {
                userList[0] = null;
                setUpBottom(view, userList[0]);
            }
        });

    }
}
