package com.example.tokyo2020;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Wishlist wish);

    @Query("SELECT attr_name from wishlist where user_id = :user_id")
    List<String> getWishlist(int user_id);
}
