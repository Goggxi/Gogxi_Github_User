package com.gogxi.githubusers.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.ui.home.HomeActivity;
import com.gogxi.githubusers.viewmodel.ViewModelFactory;

import java.util.List;
import java.util.Objects;


public class FavoriteFragment extends Fragment {
    private RecyclerView mRecyclerViewFavorite;
    private ProgressBar mProgressBarFavorite;
    private LinearLayout mLinearLayoutFavorite;
    private FavoriteAdapter mFavoriteAdapter;

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

        FavoriteVM mFavoriteVM = obtainViewModel((AppCompatActivity) Objects.requireNonNull(getActivity()));
        mFavoriteVM.getAllFavorite().observe(getViewLifecycleOwner(), favoriteObserver);


        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.favorite);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mRecyclerViewFavorite = view.findViewById(R.id.rcvw_favorite);
        mProgressBarFavorite = view.findViewById(R.id.progress_favorite);
        mLinearLayoutFavorite= view.findViewById(R.id.no_result_favorite);

        mFavoriteAdapter = new FavoriteAdapter(getActivity(), getContext());

        mRecyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFavorite.setHasFixedSize(true);
        mRecyclerViewFavorite.setAdapter(mFavoriteAdapter);

        showLoading(false);
    }

    private final Observer<List<FavoriteEntity>> favoriteObserver = userList -> {
        if (userList != null) {
            mFavoriteAdapter.setFavorite(userList);
            showLoading(true);
            if (mFavoriteAdapter.getItemCount() == 0) {
                mLinearLayoutFavorite.setVisibility(View.VISIBLE);
                mRecyclerViewFavorite.setVisibility(View.GONE);
            }
        }
    };

    @NonNull
    private static FavoriteVM obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(FavoriteVM.class);
    }

    private void showLoading(boolean state) {
        if (state){
            mProgressBarFavorite.setVisibility(View.GONE);
        } else {
            mProgressBarFavorite.setVisibility(View.VISIBLE);
        }
    }
}
