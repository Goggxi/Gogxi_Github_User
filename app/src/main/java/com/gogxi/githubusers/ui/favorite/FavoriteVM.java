package com.gogxi.githubusers.ui.favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.data.source.local.FavoriteRepository;

import java.util.List;

public class FavoriteVM extends ViewModel {
    private FavoriteRepository mFavoriteRepository;

    public FavoriteVM(Application application) {
        mFavoriteRepository = new FavoriteRepository(application);
    }

    LiveData<List<FavoriteEntity>> getAllFavorite() {
        return mFavoriteRepository.getAllFavorite();
    }
}
