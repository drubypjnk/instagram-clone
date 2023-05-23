package com.example.prm392_project.Activity;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.CommentDTO;
import com.example.prm392_project.Fragment.CommentFragment;
import com.example.prm392_project.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

        /** Resize screen when keyboard appear */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        /** Get data intent from Post Fragment*/
        Bundle extras = getIntent().getExtras();
        String content = extras.getString(CommonConstant.CONTENT);
        byte[] image = extras.getByteArray(CommonConstant.IMAGE);
        String username = extras.getString(CommonConstant.AUTHOR);
        String postId = extras.getString(CommonConstant.POST_ID);

        /** Init data post */
        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        ((ImageView)findViewById(R.id.avatar)).setImageBitmap(avatarBitmap);
        ((ImageView)findViewById(R.id.bottom_avatar)).setImageBitmap(avatarBitmap);
        ((TextView)findViewById(R.id.post_content)).setText(content);
        ((TextView)findViewById(R.id.author)).setText(username);

        /** Load existed comment */
        loadComment(postId);

        /** Init data post */
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        /** Post new comment */
        findViewById(R.id.postBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CommentActivity.this);
                String userId = sharedPreferences.getString("user_id", null);
                /** Get current content */
                String contentComment = ((TextView)findViewById(R.id.contentComment)).getText().toString();
                ApiService.apiService.addComment(userId,Integer.parseInt(postId),contentComment).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        /** Load new comment to container view*/
                        loadComment(postId);
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     *
     * @param postId
     */
    public void loadComment(String postId){
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        LinearLayout linearLayoutContainer = findViewById(R.id.linerLayoutScroll);

        /** Get list comment from database */
        ApiService.apiService.getComment(postId).enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                for (CommentDTO comment: response.body()) {
                    Fragment myFrag = new CommentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(CommonConstant.COMMENT_ID, comment.getCommentId().toString());
                    bundle.putString(CommonConstant.POST_ID,comment.getPostId() == null ? CommonConstant.EMPTY: comment.getPostId().toString());
                    bundle.putString(CommonConstant.CONTENT, comment.getContent());
                    bundle.putString(CommonConstant.AUTHOR, comment.getAuthor());
                    bundle.putString(CommonConstant.AVATAR, comment.getAvatar());
                    bundle.putString(CommonConstant.CREATE_DATE, comment.getCreateDate());
                    fragTransaction.add(linearLayoutContainer.getId(), myFrag);
                    myFrag.setArguments(bundle);
                }
                /** Remove all component before load new comment*/
                linearLayoutContainer.removeAllViews();
                fragTransaction.commit();
                Toast.makeText(CommentActivity.this,CommonConstant.SUCCESS_TOAST,Toast.LENGTH_LONG);

                /** Set data post empty after success */
                ((EditText)findViewById(R.id.contentComment)).setText(CommonConstant.EMPTY);
            }
            @Override
            public void onFailure(Call<List<CommentDTO>> call, Throwable t) {
            }
        });

    }

}
