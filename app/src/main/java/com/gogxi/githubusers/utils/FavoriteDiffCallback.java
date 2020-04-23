package com.gogxi.githubusers.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.gogxi.githubusers.data.source.local.FavoriteEntity;

import java.util.List;

public class FavoriteDiffCallback extends DiffUtil.Callback {

    private final List<FavoriteEntity> mOldFavoriteList;
    private final List<FavoriteEntity> mNewFavoriteList;

    public FavoriteDiffCallback(List<FavoriteEntity> oldFavoriteList, List<FavoriteEntity> newFavoriteList) {
        this.mOldFavoriteList = oldFavoriteList;
        this.mNewFavoriteList = newFavoriteList;
    }

    @Override
    public int getOldListSize() {
        return mOldFavoriteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewFavoriteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldFavoriteList.get(oldItemPosition).getId() == mNewFavoriteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final FavoriteEntity oldEmployee = mOldFavoriteList.get(oldItemPosition);
        final FavoriteEntity newEmployee = mNewFavoriteList.get(newItemPosition);

        return oldEmployee.getName().equals(newEmployee.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
