package com.gogxi.githubusers.data.source.local;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * from "+ FavoriteEntity.TABLE_NAME +" ORDER BY "+ FavoriteEntity.COLUMN_ID +" ASC")
    LiveData<List<FavoriteEntity>> getAllFavorite();

    @Query("SELECT COUNT(*) FROM "+ FavoriteEntity.TABLE_NAME +" WHERE "+ FavoriteEntity.COLUMN_USER_ID +" == :userId")
    LiveData<Integer> getCount(int userId);

    @Query("SELECT * FROM " + FavoriteEntity.TABLE_NAME)
    Cursor selectAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteEntity mFavoriteEntity);

    @Delete()
    void delete(FavoriteEntity mFavoriteEntity);

    @Query("Delete FROM "+ FavoriteEntity.TABLE_NAME +" where "+ FavoriteEntity.COLUMN_LOGIN +" LIKE  :login")
    void deleteUserByLogin(String login);




}
