package com.example.prm392_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Fragment.LoginFragment;
import com.example.prm392_project.R;

import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    EditText edt_uName, edt_uEmail;
    Button btn_send;
    String username, email;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_uName = findViewById(R.id.edt_username_forgot);
        edt_uEmail = findViewById(R.id.edt_email_forgot);

        btn_send = findViewById(R.id.btn_sentcode);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edt_uName.getText().toString();
                email = edt_uEmail.getText().toString();
                checkUser();
            }
        });
    }
    public void sendNewPass(String email, String newPass) {
        ApiService.apiService.sendNewPass(email, newPass).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean isSent = response.body();
                if (isSent == true) {
                    Intent myIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    ForgotPasswordActivity.this.startActivity(myIntent);
                    finish();
                    Toast.makeText(ForgotPasswordActivity.this, "New password sent to your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Some error when reset yourr pass", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgotPasswordActivity.this, "Sending reset password to your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent myIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        ForgotPasswordActivity.this.startActivity(myIntent);
        super.onBackPressed();
    }
}