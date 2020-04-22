package com.gogxi.githubusers.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.gogxi.githubusers.data.source.local.FavoriteEntity;

import java.util.List;

public class NoteDiffCallback extends DiffUtil.Callback {

    private final List<FavoriteEntity> mOldNoteList;
    private final List<FavoriteEntity> mNewNoteList;

    public NoteDiffCallback(List<FavoriteEntity> oldNoteList, List<FavoriteEntity> newNoteList) {
        this.mOldNoteList = oldNoteList;
        this.mNewNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return mOldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNoteList.get(oldItemPosition).getId() == mNewNoteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final FavoriteEntity oldEmployee = mOldNoteList.get(oldItemPosition);
        final FavoriteEntity newEmployee = mNewNoteList.get(newItemPosition);

        return oldEmployee.getName().equals(newEmployee.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
