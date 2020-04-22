package com.gogxi.githubusers.ui.detail;


import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.data.source.local.FavoriteRepository;

public class DetailVM extends ViewModel {
    private FavoriteRepository mFavoriteRepository;

    public DetailVM(Application application) {
        mFavoriteRepository = new FavoriteRepository(application);
    }

    public void insert(FavoriteEntity mFavoriteEntity) {
        mFavoriteRepository.insert(mFavoriteEntity);
    }

    public void delete(FavoriteEntity mFavoriteEntity) {
        mFavoriteRepository.delete(mFavoriteEntity);
    }
}
