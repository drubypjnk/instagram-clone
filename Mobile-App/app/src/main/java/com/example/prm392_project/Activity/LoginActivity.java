package com.example.prm392_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.UserLoginDTO;
import com.example.prm392_project.Fragment.ForgotPasswordFragment;
import com.example.prm392_project.Fragment.RegisterFragment;
import com.example.prm392_project.Interfaces.CallbackFragment;
import com.example.prm392_project.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = this.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String userName = sharedPreferences.getString("username",null);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.link_register);
        btnForgot = findViewById(R.id.link_forgot);

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
                    Toast.makeText(LoginActivity.this, "Some Input blank!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
                finish();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(myIntent);
                finish();
            }
        });
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
                        Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(myIntent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Account be deactivated! Try later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(myIntent);
        super.onBackPressed();
    }
}