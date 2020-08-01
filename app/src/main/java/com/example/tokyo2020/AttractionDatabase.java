package com.example.tokyo2020;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Attraction.class}, version = 1, exportSchema = false)
public abstract class AttractionDatabase extends RoomDatabase {

    public abstract AttractionDao attDoa();

}