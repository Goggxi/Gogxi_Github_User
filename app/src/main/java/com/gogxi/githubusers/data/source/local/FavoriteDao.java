package com.gogxi.githubusers.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteEntity mFavoriteEntity);

    @Delete()
    void delete(FavoriteEntity mFavoriteEntity);

    @Query("SELECT * from favorite ORDER BY id ASC")
    LiveData<List<FavoriteEntity>> getAllFavorite();
}
