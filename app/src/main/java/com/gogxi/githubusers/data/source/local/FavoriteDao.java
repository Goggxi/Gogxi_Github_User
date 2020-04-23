package com.gogxi.githubusers.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteEntity mFavoriteEntity);

    @Delete()
    void delete(FavoriteEntity mFavoriteEntity);

    @Query("Delete FROM favorite where login LIKE  :login")
    void deleteUserByLogin(String login);

    @Query("SELECT * from favorite ORDER BY id ASC")
    LiveData<List<FavoriteEntity>> getAllFavorite();

    @Query("SELECT COUNT(*) FROM favorite WHERE user_id == :userId")
    LiveData<Integer> getCount(int userId);
}
