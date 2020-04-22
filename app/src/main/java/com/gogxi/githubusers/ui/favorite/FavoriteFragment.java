package com.gogxi.githubusers.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.home.HomeActivity;

import java.util.Objects;


public class FavoriteFragment extends Fragment {
    private FavoriteAdapter mFavoriteAdapter = new FavoriteAdapter();
    private RecyclerView mRecyclerViewFavorite;
    private ProgressBar mProgressBarFavorite;
    private LinearLayout mLinearLayoutFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.favorite);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mRecyclerViewFavorite = view.findViewById(R.id.rcvw_favorite);
        mProgressBarFavorite = view.findViewById(R.id.progress_favorite);
        mLinearLayoutFavorite= view.findViewById(R.id.no_result_favorite);
    }
}
