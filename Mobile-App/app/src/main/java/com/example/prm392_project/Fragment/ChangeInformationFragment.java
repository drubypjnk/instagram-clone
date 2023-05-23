package com.example.prm392_project.Fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.DTO.PhotoInforDTO;
import com.example.prm392_project.DTO.TestDTO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.Model.User;
import com.example.prm392_project.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInformationFragment extends Fragment {
    Button btEditSubmit;
    EditText edtUserName, edtName, edtDesc, edtEmail, edtAddress, edtAge;
    TextView tvMess, tvChangeImage;
    ImageView imgAvtar;
    ActivityResultLauncher<String> mGetContent;
    RadioButton rbMale, rbFemale;
    Uri uri;
    UserInforDTO userInforDTO;

    public ChangeInformationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_form, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String user_id = sharedPreferences.getString("user_id", null);
        tvChangeImage = view.findViewById(R.id.tvEditAvartar);
        imgAvtar = view.findViewById(R.id.profile_image_info);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            String base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            PhotoInforDTO photoInforDTO = new PhotoInforDTO();
                            photoInforDTO.setContent(base64String);
                            photoInforDTO.setUrl(uri.toString());
                            userInforDTO.setAvartarImage(photoInforDTO);
                            // sử dụng base64String ở đây để lưu vào cơ sở dữ liệu của bạn
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // Handle the returned Uri
                        uri = uri;
                        imgAvtar.setImageURI(uri);
                    }
                });
        getUser(user_id, view);

    }

    private void getUser(String user_id, View view) {
        ApiService.apiService.getUserByID(user_id).enqueue(new Callback<UserInforDTO>() {
            @Override
            public void onResponse(Call<UserInforDTO> call, Response<UserInforDTO> response) {
                userInforDTO = response.body();

                onInit(view);
            }

            @Override
            public void onFailure(Call<UserInforDTO> call, Throwable t) {
                userInforDTO = null;
                onInit(view);
            }
        });

    }

    private void Action() {
        setUpfield();
        btEditSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateForm()) {
                    if (checkUserExist()) {
                        save();
                    }
                } else {
                    tvMess.setText("All field must required");
                    tvMess.setVisibility(TextView.VISIBLE);
                }
//            save();
            }
        });


        tvChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}
                            , 999);
                }
                mGetContent.launch("image/*");
            }
        });
    }

    private void onInit(View view) {
        edtUserName = view.findViewById(R.id.edt_UserName);
        edtName = view.findViewById(R.id.edt_Name);
        edtDesc = view.findViewById(R.id.edt_Desc);
        edtEmail = view.findViewById(R.id.edt_Email);
        edtAddress = view.findViewById(R.id.edt_Address);
        btEditSubmit = view.findViewById(R.id.btSaveUserProfile);
        tvMess = view.findViewById(R.id.tvMessage_changeProfile);
        rbFemale = view.findViewById(R.id.rbFemale);
        rbMale = view.findViewById(R.id.rbMale);
        edtAge = view.findViewById(R.id.edt_Age);
        tvMess.setVisibility(TextView.INVISIBLE);
        Action();
    }

    private void setUpfield() {
        edtUserName.setText(userInforDTO.getUsername());
        edtName.setText(userInforDTO.getFullName());
        edtDesc.setText(userInforDTO.getDesription());
        edtEmail.setText(userInforDTO.getEmail());
        edtAddress.setText(userInforDTO.getLocation());
        edtAge.setText(userInforDTO.getAge() + "");
        imgAvtar.setImageBitmap(getImageBase64(userInforDTO.getAvartarImage().getContent()));
        if (userInforDTO.gender == 1) {
            rbMale.setChecked(true);
        } else {
            rbFemale.setChecked(false);
        }
    }

    private boolean ValidateForm() {
        boolean check = true;
        if (edtUserName.getText().toString().isEmpty()) {
            check = false;
        }
        if (edtName.getText().toString().isEmpty()) {
            check = false;
        }
        if (edtDesc.getText().toString().isEmpty()) {
            check = false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            check = false;
        }
        if (edtAddress.getText().toString().isEmpty()) {
            check = false;
        }
        if (edtAge.getText().toString().isEmpty()) {
            check = false;
        }
        return check;
    }

    private boolean checkUserExist() {
        if (edtUserName.getText().toString().equals("dung")) {
            tvMess.setVisibility(TextView.VISIBLE);
            tvMess.setText("User have exist");
            return false;
        }
        return true;
    }

    private void save() {
        boolean check = saveUser();
        if (check == true) {
            tvMess.setVisibility(TextView.VISIBLE);
            tvMess.setText("Save successfully !!");
            tvMess.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvMess.setVisibility(TextView.VISIBLE);
            tvMess.setText("Save failed !!");
            tvMess.setTextColor(getResources().getColor(R.color.red));
        }

    }

    private boolean saveUser() {
        final Boolean[] check = {true};
        UserInforDTO u = this.userInforDTO;
        u.setAvartarImage(userInforDTO.getAvartarImage());
        u.setUsername(edtUserName.getText().toString());
        u.setFullName(edtName.getText().toString());
        u.setDesription(edtDesc.getText().toString());
        u.setEmail(edtEmail.getText().toString());
        u.setLocation(edtAddress.getText().toString());
        u.setAge(Integer.parseInt(edtAge.getText().toString()));
        if (rbMale.isChecked()) {
            u.setGender(1);
        } else {
            u.setGender(0);
        }
        ApiService.apiService.saveInformation(u).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body().booleanValue() == true) {
                    check[0] = true;
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                check[0] = false;
            }
        });
        return check[0];
    }

    public Bitmap getImageBase64(String base64String) {
// Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

// Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
