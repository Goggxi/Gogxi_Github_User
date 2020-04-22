package com.gogxi.githubusers.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.ui.detail.following.FollowingVM;
import com.gogxi.githubusers.ui.home.HomeActivity;
import com.gogxi.githubusers.viewmodel.ViewModelFactory;

import java.util.List;
import java.util.Objects;


public class FavoriteFragment extends Fragment {
//    private FavoriteAdapter mFavoriteAdapter = new FavoriteAdapter();
    private RecyclerView mRecyclerViewFavorite;
    private ProgressBar mProgressBarFavorite;
    private LinearLayout mLinearLayoutFavorite;
//    private FavoriteVM mFavoriteVM;
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

        FavoriteVM mFavoriteVM = obtainViewModel((AppCompatActivity) getActivity());
        mFavoriteVM.getAllFavorite().observe(getViewLifecycleOwner(), favoriteObserver);

        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.favorite);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mRecyclerViewFavorite = view.findViewById(R.id.rcvw_favorite);
        mProgressBarFavorite = view.findViewById(R.id.progress_favorite);
        mLinearLayoutFavorite= view.findViewById(R.id.no_result_favorite);

//        getFavoriteUser();

        mFavoriteAdapter = new FavoriteAdapter(getActivity());

        mRecyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFavorite.setHasFixedSize(true);
        mRecyclerViewFavorite.setAdapter(mFavoriteAdapter);
    }

    private final Observer<List<FavoriteEntity>> favoriteObserver = userList -> {
        if (userList != null) {
            mFavoriteAdapter.setFavorite(userList);
        }
    };

    @NonNull
    private static FavoriteVM obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(FavoriteVM.class);
    }

//    private void getFavoriteUser(){
//        mFavoriteVM = new ViewModelProvider(this).get(FavoriteVM.class);
////        mSearchVM.setResultUsers(getString(R.string.language));
//        mFavoriteVM.getAllFavorite().observe(getViewLifecycleOwner(),getUser);
//        mRecyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerViewFavorite.setHasFixedSize(true);
//        mRecyclerViewFavorite.setAdapter(mFavoriteAdapter);
//    }
//
//    private Observer<List<FavoriteEntity>> getUser = users -> {
//        if (users != null){
//            mFavoriteAdapter.notifyDataSetChanged();
//            mFavoriteAdapter.setFavorite(users);
////            showLoading(true);
////            if (mSearchAdapter.getItemCount() == 0){
////                mLinearLayoutFollowing.setVisibility(View.VISIBLE);
////                mRecyclerViewFollowing.setVisibility(View.GONE);
////            }
//        }
//    };
}
