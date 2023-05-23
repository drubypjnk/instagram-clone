package com.example.prm392_project.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.CommentActivity;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.PostDTO;
import com.example.prm392_project.R;
import com.example.prm392_project.Animation.SmallBangView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {
    SmallBangView heartBeat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate the layout for this fragment */
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.post_fragment, container, false);
        heartBeat = view.findViewById(R.id.heart_beat);

        /** Get view */
        ImageView imagePost = (ImageView) view.findViewById(R.id.image_post);
        ImageView topAvatar = (ImageView) view.findViewById(R.id.avatar);
        TextView otherLiked = (TextView) view.findViewById(R.id.other_liked);
        TextView postId = (TextView) view.findViewById(R.id.postId);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView authorName = (TextView) view.findViewById(R.id.main_author);
        TextView bottomAuthor = (TextView) view.findViewById(R.id.author_bottom);
        TextView content = (TextView) view.findViewById(R.id.content);

        PostDTO postDTO = MyApp.postDTOList.get((Integer) getArguments().get("index"));

//        /** Decode base64 to bitmap */
//        byte[] decodedImgPost = Base64.decode(getArguments().get(CommonConstant.POST_IMAGE).toString(), Base64.DEFAULT);
//        byte[] decodedAvatar = Base64.decode(getArguments().get(CommonConstant.AVATAR).toString(), Base64.DEFAULT);
//        Bitmap postBitmap = BitmapFactory.decodeByteArray(decodedImgPost, 0, decodedImgPost.length);
//        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(decodedAvatar, 0, decodedAvatar.length);
//
//        /** Set data to view */
//        imagePost.setImageBitmap(postBitmap);
//        topAvatar.setImageBitmap(avatarBitmap);
//        otherLiked.setText(getArguments().get(CommonConstant.TOTAL_LIKED).toString());
//        postId.setText(getArguments().get(CommonConstant.POST_ID).toString());
//        location.setText(getArguments().get(CommonConstant.LOCATION).toString());
//        authorName.setText(getArguments().get(CommonConstant.AUTHOR).toString());
//        bottomAuthor.setText(getArguments().get(CommonConstant.AUTHOR).toString());
//        content.setText(getArguments().get(CommonConstant.CONTENT).toString());
//        heartBeat.setSelected(Boolean.parseBoolean(getArguments().get(CommonConstant.IS_LIKED).toString()));

        /** Decode base64 to bitmap */
        byte[] decodedImgPost = Base64.decode(postDTO.getImage(), Base64.DEFAULT);
        byte[] decodedAvatar = Base64.decode(postDTO.getAvatar(), Base64.DEFAULT);
        Bitmap postBitmap = BitmapFactory.decodeByteArray(decodedImgPost, 0, decodedImgPost.length);
        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(decodedAvatar, 0, decodedAvatar.length);

        /** Set data to view */
        imagePost.setImageBitmap(postBitmap);
        topAvatar.setImageBitmap(avatarBitmap);
        otherLiked.setText(postDTO.getTotalLike().toString());
        postId.setText(postDTO.getPostId().toString());
        location.setText(postDTO.getLocation());
        authorName.setText(postDTO.getUsername());
        bottomAuthor.setText(postDTO.getUsername());
        content.setText(postDTO.getContent());
        heartBeat.setSelected(postDTO.getLiked());

        /** Like post event */
        heartBeat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentTotalLiked = Integer.parseInt(otherLiked.getText().toString());

                        /** Update like status of post */
                        ApiService.apiService.changeLikeStatus(MyApp.GetUserId(), postDTO.getPostId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {}
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {}
                        });

                        /** Set like animation, enable and disable like */
                        if (heartBeat.isSelected()) {
                            heartBeat.setSelected(false);
                            otherLiked.setText(Integer.toString((currentTotalLiked - 1)));
                        } else {
                            heartBeat.setSelected(true);
                            heartBeat.likeAnimation();
                            otherLiked.setText(Integer.toString((currentTotalLiked + 1)));
                        }
                    }
                });

        /** Send data to {@link CommentActivity} */
        view.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                /** Redirect to {@link CommentActivity}*/
                Intent intent = new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra(CommonConstant.CONTENT, content.getText().toString());
                intent.putExtra(CommonConstant.IMAGE, decodedAvatar);
                intent.putExtra(CommonConstant.USERNAME, authorName.getText().toString());
                intent.putExtra(CommonConstant.POST_ID, postId.getText().toString());

                transaction.addToBackStack("backStack");
                transaction.commit();
                startActivity(intent);
            }
        });
        authorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = MainActivity.getInstance();
                main.redirectToUserProfileFragment(postDTO.getUserId());
            }
        });
        return view;
    }
}
