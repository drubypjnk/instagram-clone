package com.example.prm392_project.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.prm392_project.DTO.PostDTO;
import com.example.prm392_project.DTO.UserInforDTO;
import com.example.prm392_project.Database.InstaDatabase;
import com.example.prm392_project.Entity.SearchKey;
import com.example.prm392_project.Model.ActivityContainer;

import java.util.List;

public class MyApp extends Application {
    public static String BASE_URL = "http://10.33.16.126:6868/";
    public static List<ActivityContainer> activityContainers;

    public static List<UserInforDTO> userInforDTOS;

    public static List<SearchKey> recentSearch;

    private static String userId;

    public static void SetUserId(String abc){
        userId = abc;
    }
    public static String GetUserId(){
        return userId;
    }
    public  static String DATE_FORMAT= "dd/MM/yyyy";
    public static List<PostDTO> postDTOList;
}
