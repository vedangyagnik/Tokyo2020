package com.example.tokyo2020;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AttractionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Attraction att);

    @Query("SELECT * from attraction")
    List<Attraction> getAttractionList();
}
