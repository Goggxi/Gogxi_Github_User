package com.gogxi.githubfavoriteuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.gogxi.githubfavoriteuser.adapter.FavoriteAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.gogxi.githubusers.ui.provider";
    public static final Uri URI_USER = Uri.parse("content://" + AUTHORITY + "/" + "favorite");
    private static final int LOADER = 1;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvFavorite = findViewById(R.id.rv_favorite);


        favoriteAdapter = new FavoriteAdapter(this, this);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvFavorite.setAdapter(favoriteAdapter);

        LoaderManager.getInstance(this).initLoader(LOADER,null,mLoaderCallbacks);
//        getSupportLoaderManager().initLoader(LOADER,null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    if (id == LOADER) {
                        return new CursorLoader(
                                getApplicationContext(),
                                URI_USER,
                                null,
                                null,
                                null,
                                null);
                    }else {
                        throw new IllegalArgumentException();
                    }

                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                    if (loader.getId() == LOADER) {
                        favoriteAdapter.refill(data);
                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                    if (loader.getId() == LOADER) {
                        favoriteAdapter.refill(null);
                    }
                }
            };
}
