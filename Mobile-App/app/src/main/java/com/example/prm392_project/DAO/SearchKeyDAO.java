package com.example.prm392_project.DAO;

import androidx.room.*;
import com.example.prm392_project.Entity.SearchKey;

import java.util.List;

@Dao
public interface SearchKeyDAO {
    @Query("SELECT * FROM searchKeys order by createdDate desc LIMIT 30")
    List<SearchKey> getAll();

    @Update
    void update(SearchKey searchKey);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SearchKey... searchKeys);
}
