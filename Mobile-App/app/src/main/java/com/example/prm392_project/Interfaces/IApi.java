package com.example.prm392_project.Interfaces;

import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.MessageDTO;
import com.example.prm392_project.DTO.RoomDTO;
import com.example.prm392_project.Model.Message;
import com.example.prm392_project.Model.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").create();
    IApi myApi = new Retrofit.Builder().baseUrl(MyApp.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(IApi.class);
    @GET("api/Rooms/getRooms/{userId}")
    Call<List<RoomDTO>> getRooms(@Path("userId") String userId);
    @GET("api/Rooms/getRoom/{userId}/{roomId}")
    Call<RoomDTO> getRoom(@Path("userId") String userId, @Path("roomId") int roomId);
    @GET("api/Messages/getMessages/{roomId}")
    Call<List<MessageDTO>> getMessages(@Path("roomId") int roomId);
    @POST("api/Messages/")
    Call<Message> sendMessages(@Query("userId") String userId, @Query("roomId") int roomId, @Query("messageContent") String messageContent);
    @GET("api/Rooms/getRoomByUsers/{userId1}/{userId2}")
    Call<RoomDTO> getRoomByUsers(@Path("userId1") String userId1, @Path("userId2") String userId2);

}
