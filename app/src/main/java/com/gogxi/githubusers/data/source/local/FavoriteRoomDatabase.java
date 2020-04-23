package com.gogxi.githubusers.data.source.local;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteEntity.class}, version = 1)
public abstract class FavoriteRoomDatabase extends RoomDatabase {

    private static volatile FavoriteRoomDatabase INSTANCE;

    public static FavoriteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteRoomDatabase.class, "db_User")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteDao favoriteDao();
}
