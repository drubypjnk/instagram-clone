package com.example.prm392_project.Database;

import android.content.Context;
import androidx.room.*;
import com.example.prm392_project.DAO.SearchKeyDAO;
import com.example.prm392_project.Entity.SearchKey;
import com.example.prm392_project.Utils.Converter;

@Database(entities = {SearchKey.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class InstaDatabase extends RoomDatabase {
    public abstract SearchKeyDAO searchKeyDAO();

    private static InstaDatabase INSTANCE;

    public static synchronized InstaDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (InstaDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,
                                    InstaDatabase.class,
                                    "insta_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
