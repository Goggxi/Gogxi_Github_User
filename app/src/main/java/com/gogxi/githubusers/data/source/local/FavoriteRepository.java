package com.gogxi.githubusers.data.source.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteRepository {

    private FavoriteDao mFavoriteDao;
    private ExecutorService executorService;

    public FavoriteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();

        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        mFavoriteDao = db.favoriteDao();
    }

    public LiveData<List<FavoriteEntity>> getAllFavorite() {
        return mFavoriteDao.getAllFavorite();
    }

    public LiveData<Integer> getCount(int userId) {
        return mFavoriteDao.getCount(userId);
    }

    public void insert(final FavoriteEntity mFavoriteEntity) {
        executorService.execute(() -> mFavoriteDao.insert(mFavoriteEntity));
    }

    public void delete(final FavoriteEntity mFavoriteEntity){
        executorService.execute(() -> mFavoriteDao.delete(mFavoriteEntity));
    }

    public void deleteByLogin(String login){
        executorService.execute(() -> mFavoriteDao.deleteUserByLogin(login));
    }
}
