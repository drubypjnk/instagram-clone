package com.example.prm392_project.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post_activity);

        Bundle extras = getIntent().getExtras();

        /** Get data from {@link com.example.prm392_project.Fragment.PostDetailFragment} **/
        byte[] decodedImgPost = extras.getByteArray(CommonConstant.POST_IMAGE);
        Bitmap postBitmap = BitmapFactory.decodeByteArray(decodedImgPost, 0, decodedImgPost.length);
        String content = extras.get(CommonConstant.CONTENT).toString();
        Integer postId = Integer.parseInt(extras.get(CommonConstant.POST_ID).toString());

        ((EditText) findViewById(R.id.caption_edit)).setText(content);
        ((ImageView) findViewById(R.id.image_post_edit)).setImageBitmap(postBitmap);

        /** Save click event */
        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentContent = ((EditText) findViewById(R.id.caption_edit)).getText().toString();
                ApiService.apiService.updatePost(postId, currentContent).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
        });

        /** Back click event **/
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
