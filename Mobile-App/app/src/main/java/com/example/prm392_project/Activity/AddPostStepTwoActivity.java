package com.example.prm392_project.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.NewPostDTO;
import com.example.prm392_project.R;
import com.example.prm392_project.Utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class AddPostStepTwoActivity extends AppCompatActivity {
    String encodedImage;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_step_two_activity);

        /** Get data from {@link AddPostStepOneActivity}*/
        Bundle extras = getIntent().getExtras();
        String imagePath = extras.getString(CommonConstant.IMAGE);

        File imgFile = new File(imagePath);

        /** Decode base64 to bitmap and set it to imageSelected*/
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        final Bitmap[] myBitmap = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(imagePath)
                            .addListener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                    // Handle error
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                    // Use the bitmap on the main thread
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Update UI with bitmap
                                            myBitmap[0] = resource;
                                            ImageView imageSelected = findViewById(R.id.image_selected_2nd);
                                            imageSelected.setImageBitmap(myBitmap[0]);
                                            byte[] byteArray = ImageUtils.getResizedImage(myBitmap[0], imgFile.getAbsolutePath(), getApplicationContext());
                                            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                        }
                                    });
                                    return false;
                                }
                            })
                            .submit()
                            .get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
//        ImageView imageSelected = findViewById(R.id.image_selected_2nd);
//        imageSelected.setImageBitmap(myBitmap[0]);

//        /** Convert bitmap to String */
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = ImageUtils.getResizedImage(myBitmap[0], imgFile.getAbsolutePath(), getApplicationContext());
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        /** Back to {@link AddPostStepOneActivity}*/
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(AddPostStepTwoActivity.this,AddPostStepOneActivity.class);
                startActivity(intentBack);
            }
        });

        /** Create new post event */
        findViewById(R.id.share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddPostStepTwoActivity.this);
                String userId = sharedPreferences.getString("user_id", null);
                NewPostDTO newPostDTO = new NewPostDTO();
                newPostDTO.setImage(encodedImage);
                newPostDTO.setContent(((EditText)findViewById(R.id.caption)).getText() == null ? CommonConstant.EMPTY :((EditText)findViewById(R.id.caption)).getText().toString());
                newPostDTO.setTitle(CommonConstant.EMPTY);
                newPostDTO.setUserId(userId);

                /** Add new post */
                ApiService.apiService.addNewPost(newPostDTO).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        /** Redirect to {@link MainActivity} */
                        Intent intent = new Intent(AddPostStepTwoActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
        });
    }
}
