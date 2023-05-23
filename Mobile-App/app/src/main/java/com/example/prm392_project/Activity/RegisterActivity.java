package com.example.prm392_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.DTO.UserRegisterDTO;
import com.example.prm392_project.Fragment.LoginFragment;
import com.example.prm392_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = this.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtEmail = findViewById(R.id.edt_email);
        edtRePassword = findViewById(R.id.edt_repassword);
        btnRegister = findViewById(R.id.btn_register);
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
                        Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
                        mess="";
                    } else {
                        checkUsername();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Some Input is blank!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
                    mess="";
                } else {
                    if (!email.contains("@")) {
                        if (mess == "") {
                            mess += "Email not have '@'";
                        } else {
                            mess += "\nEmail not have '@'";
                        }
                        Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
                    mess="";
                } else {
                    if (!repassword.equals(password)) {
                        if (mess == "") {
                            mess += "Password and RePassword not similar";
                        } else {
                            mess += "\nPassword and RePassword not similar";
                        }
                        Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
                        mess="";
                    }
                    if (mess == "") {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                        register(new UserRegisterDTO(username,email,password));

                    } else {
                        Toast.makeText(RegisterActivity.this, mess, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "Registered Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
        super.onBackPressed();
    }

}