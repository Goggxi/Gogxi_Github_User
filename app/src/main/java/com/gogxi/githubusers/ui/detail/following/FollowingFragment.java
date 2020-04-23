package com.gogxi.githubusers.ui.detail.following;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.ui.search.SearchAdapter;

import java.util.List;

public class FollowingFragment extends Fragment {
    public static final String EXTRA_FOLLOWING = "extra_following";
    public static final String EXTRA_FOLLOWING_LOCAL = "extra_following_local";
    private SearchAdapter mSearchAdapter = new SearchAdapter();
    private RecyclerView mRecyclerViewFollowing;
    private ProgressBar mProgressBarFollowing;
    private LinearLayout mLinearLayoutFollowing;
    private String username;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewFollowing = view.findViewById(R.id.rcvw_following);
        mProgressBarFollowing = view.findViewById(R.id.progress_following);
        mLinearLayoutFollowing = view.findViewById(R.id.no_result_following);

        Bundle arguments = getArguments();
        if (arguments != null){
            username = arguments.getString(FollowingFragment.EXTRA_FOLLOWING);
            username = arguments.getString(FollowingFragment.EXTRA_FOLLOWING_LOCAL);
        }

        getFollowing(username);
        showLoading(false);
    }

    private void getFollowing(String path){
        FollowingVM mFollowingVM = new ViewModelProvider(this).get(FollowingVM.class);
//        mSearchVM.setResultUsers(getString(R.string.language));
        mFollowingVM.setFollowingUsers(path);
        mFollowingVM.getFollowingUsers().observe(getViewLifecycleOwner(),getUser);
        mRecyclerViewFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFollowing.setHasFixedSize(true);
        mRecyclerViewFollowing.setAdapter(mSearchAdapter);
    }

    private Observer<List<Users>> getUser = users -> {
        if (users != null){
            mSearchAdapter.notifyDataSetChanged();
            mSearchAdapter.setUsers(users);
            showLoading(true);
            if (mSearchAdapter.getItemCount() == 0){
                mLinearLayoutFollowing.setVisibility(View.VISIBLE);
                mRecyclerViewFollowing.setVisibility(View.GONE);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state){
            mProgressBarFollowing.setVisibility(View.GONE);
        } else {
            mProgressBarFollowing.setVisibility(View.VISIBLE);
        }
    }
}
