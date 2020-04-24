package com.gogxi.githubusers.ui.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.data.source.local.FavoriteRoomDatabase;

import java.util.Objects;

@SuppressLint("Registered")
public class FavoriteContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.gogxi.githubusers.ui.provider";

    public static final Uri URI_CHEESE = Uri.parse(
            "content://" + AUTHORITY + "/" + FavoriteEntity.TABLE_NAME);

    private FavoriteRoomDatabase mFavoriteRoomDatabase;

    private static final int CODE_DIR = 1;
    private static final int CODE_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, FavoriteEntity.TABLE_NAME, CODE_DIR);
        MATCHER.addURI(AUTHORITY, FavoriteEntity.TABLE_NAME + "/*", CODE_ITEM);
    }

    @Override
    public boolean onCreate() {
        mFavoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_DIR || code == CODE_ITEM){
            final Context context = getContext();

            if (context == null) {
                return null;
            }

            mFavoriteRoomDatabase = (FavoriteRoomDatabase) FavoriteRoomDatabase.getDatabase(context).favoriteDao();
            Cursor cursor = null;

            if (code == CODE_DIR) {
                cursor = mFavoriteRoomDatabase.favoriteDao().selectAll();
            }
            Objects.requireNonNull(cursor).setNotificationUri(context.getContentResolver(), uri);
            return cursor;

        }else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
