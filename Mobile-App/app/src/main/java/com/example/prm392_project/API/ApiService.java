package com.example.prm392_project.API;

import com.example.prm392_project.DTO.CommentDTO;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.FollowInforDTO;
import com.example.prm392_project.DTO.NewPostDTO;
import com.example.prm392_project.DTO.PostDTO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.DTO.UserLoginDTO;
import com.example.prm392_project.DTO.UserPassDTO;
import com.example.prm392_project.DTO.UserRegisterDTO;
import com.example.prm392_project.DTO.UserToManageDTO;
import com.example.prm392_project.Model.ActivityContainer;
import com.example.prm392_project.Model.User;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.*;
import com.example.prm392_project.Model.ActivityContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    ApiService apiService=new Retrofit.Builder().baseUrl(MyApp.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService.class);
    @POST("/checkUserId")
    Call<UserPassDTO> checkUser(@Query("userId")String userId);
    @POST("/changePassword")
    Call<Boolean> changePassword(@Body UserPassDTO userPassDTO);
    @POST("/saveInformation")
    Call<Boolean> saveInformation(@Body UserInforDTO userInforDTO);
    @POST("/register")
    Call<Boolean> register(@Body UserRegisterDTO userRegisterDTO);
    @POST("/activeUser")
    Call<Boolean> activeUser(@Query("user_id") String userId);
    @POST("/deActiveUser")
    Call<Boolean> deActiveUser(@Query("user_id") String userId);
    @POST("/activePost")
    Call<Boolean> activePost(@Query("post_id") int post_id);
    @POST("/deActivePost")
    Call<Boolean> deActivePost(@Query("post_id") int post_id);
    @POST("/sendNewPass")
    Call<Boolean> sendNewPass(@Query("email") String email,@Query("newPass") String newPass);

    @GET("/getUserInformation")
    Call<UserInforDTO> getUserByID(@Query("user_id")String userId);

    @GET("/getUserLogin")
    Call<UserLoginDTO> getUserLogin(@Query("username")String username, @Query("password")String password);
    @GET("/getUserByUsername")
    Call<UserInforDTO> getUserByUsername(@Query("username")String username);
    @GET("/getUserByEmail")
    Call<UserInforDTO> getUserByEmail(@Query("email") String email);
    @POST("/getUserByUsernameAndEmail")
    Call<Boolean> getUserByUsernameAndEmail(@Query("username")String username,@Query("email") String email);

    @GET("/getFollowingByUserId")
    Call<List<FollowInforDTO>> getFollowingByUserId(@Query("user_id")String userId);
    @GET("/getFollowerByUserId")
    Call<List<FollowInforDTO>> getFollowerByUserId(@Query("user_id")String userId);
    @GET("/getFollowingUser")
    Call<List<FollowInforDTO>> getFollowingUser(@Query("user_id")String userId,@Query("view_id")String view_id);
    @GET("/getFollowerUser")
    Call<List<FollowInforDTO>> getFollowerUser(@Query("user_id")String userId,@Query("view_id")String view_id);
    @GET("/getUserToManage")
    Call<List<UserToManageDTO>> getUserToManage(@Query("user_id")String userId);

    @POST("/unfollowUser")
    Call<Boolean> unfollowUser(@Query("followId")String followId);
    @POST("/refollowUser")
    Call<Boolean> refollowUser(@Query("followId")String followId);
    @POST("/followUser")
    Call<Boolean> followUser(@Query("user_id")String user_id,@Query("follow_To")String follow_To);
    @GET("/Notification")
    Call<List<ActivityContainer>> getActivities(@Query("userId") String userId);
    @GET("/SearchUser")
    Call<List<UserInforDTO>> searchUser(@Query("userId") String userId);
    @POST("/unFollower")
    Call<Boolean> unFollower(@Query("user_id")String user_id,@Query("follower")String follower);

    /**
     * @param userId
     * @return
     */
    @GET("/post/all")
    Call<List<PostDTO>> getListPost(@Query("user_id") String userId);

    @GET("/post/manage")
    Call<List<PostItemDTO>> getListPostToManage();

    @POST("/post/add")
    Call<Void> addNewPost(@Body NewPostDTO newPostDTO);

    @GET("/comment/all")
    Call<List<CommentDTO>> getComment(@Query("post_id") String userId);

    @POST("/like/insert")
    Call<Void> changeLikeStatus(@Query("user_id") String userId, @Query("post_id") Integer postId);

    @POST("/comment/add")
    Call<Void> addComment(@Query("user_id") String userId, @Query("post_id") Integer postId, @Query("content") String content);

    @GET("/post/detail")
    Call<PostDTO> getSinglePost(@Query("post_id") Integer postId);

    @PUT("/post/update")
    Call<Void> updatePost(@Query("post_id") Integer postId, @Query("content") String content);
}
