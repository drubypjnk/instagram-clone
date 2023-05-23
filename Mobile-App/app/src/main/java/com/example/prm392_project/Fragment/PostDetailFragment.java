package com.example.prm392_project.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.CommentActivity;
import com.example.prm392_project.Activity.EditPostActivity;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Animation.SmallBangView;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.DTO.PostDTO;
import com.example.prm392_project.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailFragment extends Fragment {
    SmallBangView heartBeat;
    String currentPostId, currentContent;
    byte[] currentAvatar, currentPostImage;
    ImageView imagePost, topAvatar;
    TextView content, bottomAuthor, authorName, location, postId, otherLiked;

    View mainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate the layout for this fragment */
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.post_detail_fragment, container, false);
        heartBeat = mainView.findViewById(R.id.heart_beat);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
        String userId = sharedPreferences.getString("user_id", null);

        /** Get view */
        imagePost = (ImageView) mainView.findViewById(R.id.image_post);
        topAvatar = (ImageView) mainView.findViewById(R.id.avatar);
        otherLiked = (TextView) mainView.findViewById(R.id.other_liked);
        postId = (TextView) mainView.findViewById(R.id.postId);
        location = (TextView) mainView.findViewById(R.id.location);
        authorName = (TextView) mainView.findViewById(R.id.main_author);
        bottomAuthor = (TextView) mainView.findViewById(R.id.author_bottom);
        content = (TextView) mainView.findViewById(R.id.content);

        loadDataToView(mainView);

        /** Like post event */
        heartBeat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentTotalLiked = Integer.parseInt(otherLiked.getText().toString());

                        /** Update like status of post */
                        ApiService.apiService.changeLikeStatus(userId,
                                Integer.parseInt(currentPostId)).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                            }
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
        mainView.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** Redirect to {@link CommentActivity}*/
                Intent intent = new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra(CommonConstant.CONTENT, content.getText().toString());
                intent.putExtra(CommonConstant.IMAGE, currentAvatar);
                intent.putExtra(CommonConstant.USERNAME, authorName.getText().toString());
                intent.putExtra(CommonConstant.POST_ID, postId.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /** Back to {@link TablayoutUserFragment} */
        mainView.findViewById(R.id.backToProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastTagFragment = getLastTagFragment();
                if (lastTagFragment.equalsIgnoreCase("Myprofile_Postdetail_Backstack")) {
                    MainActivity.getInstance().redirectToMyProfle();
                } else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });


        /** Show more event*/
        mainView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view.findViewById(R.id.more));

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_option:
                                Intent intent = new Intent(view.getContext(), EditPostActivity.class);
                                intent.putExtra(CommonConstant.POST_ID, currentPostId);
                                intent.putExtra(CommonConstant.CONTENT, currentContent);
                                intent.putExtra(CommonConstant.POST_IMAGE, currentPostImage);
                                startActivity(intent);
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.menu_post_detail);

                /** Insert icon to menu */
                insertMenuItemIcons(view.getContext(), popupMenu);
                popupMenu.show();
            }
        });
        return mainView;
    }

    public String getLastTagFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        int index = fragmentManager.getBackStackEntryCount()-1;
        FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        return tag;
    }

    public void loadDataToView(View view) {
        /** Set data to view */
        ApiService.apiService.getSinglePost(Integer.parseInt(getArguments().get(CommonConstant.POST_ID).toString())).enqueue(new Callback<PostDTO>() {
            @Override
            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                PostDTO post = response.body();

                if (!Objects.isNull(post)) {
                    /** Decode base64 to bitmap */
                    byte[] decodedImgPost = Base64.decode(post.getImage(), Base64.DEFAULT);
                    byte[] decodedAvatar = Base64.decode(post.getAvatar(), Base64.DEFAULT);
                    Bitmap postBitmap = BitmapFactory.decodeByteArray(decodedImgPost, 0, decodedImgPost.length);
                    Bitmap avatarBitmap = BitmapFactory.decodeByteArray(decodedAvatar, 0, decodedAvatar.length);

                    currentAvatar = decodedAvatar;
                    currentPostId = post.getPostId().toString();
                    currentContent = post.getContent();
                    currentPostImage = decodedImgPost;

                    imagePost.setImageBitmap(postBitmap);
                    topAvatar.setImageBitmap(avatarBitmap);

                    otherLiked.setText(post.getTotalLike().toString());
                    postId.setText(post.getPostId().toString());
                    location.setText(post.getLocation());
                    authorName.setText(post.getUsername());
                    bottomAuthor.setText(post.getUsername());
                    content.setText(post.getContent());
                    heartBeat.setSelected(post.getLiked());
                    ((TextView) view.findViewById(R.id.author_header)).setText(post.getUsername());
                } else {
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    Fragment errorFragment = new PostNotFoundFragment();
                    transaction.replace(R.id.fragment_container, errorFragment, "errorFragment");
                    transaction.addToBackStack("errorFragment");
                    transaction.commit();
                }
            }

            @Override
            public void onFailure(Call<PostDTO> call, Throwable t) {
            }
        });
    }

    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }

    /**
     * @return true if the menu has at least one MenuItem with an icon.
     */
    private static boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null) return true;
        }
        return false;
    }

    /**
     * Converts the given MenuItem's title into a Spannable containing both its icon and title.
     */
    private static void insertMenuItemIcon(Context context, MenuItem menuItem) {
        Drawable icon = menuItem.getIcon();

        // If there's no icon, we insert a transparent one to keep the title aligned with the items
        // which do have icons.
        if (icon == null) icon = new ColorDrawable(Color.TRANSPARENT);

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.menu_item_icon_size);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + menuItem.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        menuItem.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        menuItem.setIcon(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataToView(mainView);
    }
}

