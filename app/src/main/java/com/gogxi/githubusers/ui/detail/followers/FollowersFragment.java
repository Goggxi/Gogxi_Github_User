package com.gogxi.githubusers.ui.detail.followers;

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

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.ui.search.SearchAdapter;

import java.util.List;

public class FollowersFragment extends Fragment {
    public static final String EXTRA_FOLLOWERS = "extra_followers";
    private SearchAdapter mSearchAdapter = new SearchAdapter();
    private RecyclerView mRecyclerViewFollowers;
    private String username;

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewFollowers = view.findViewById(R.id.rcvw_followers);

        if (getArguments() != null){
            username = getArguments().getString(EXTRA_FOLLOWERS);
        }

       getFollowers(username);
    }

    private void getFollowers(String path){
        FollowersVM mFollowersVM = new ViewModelProvider(this).get(FollowersVM.class);
//        mSearchVM.setResultUsers(getString(R.string.language));
        mFollowersVM.setFollowersUsers(path);
        mFollowersVM.getFollowersUsers().observe(this,getUser);
        mRecyclerViewFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFollowers.setHasFixedSize(true);
        mRecyclerViewFollowers.setAdapter(mSearchAdapter);
    }

    private Observer<List<Users>> getUser = users -> {
        if (users != null){
            mSearchAdapter.notifyDataSetChanged();
            mSearchAdapter.setUsers(users);
//            showLoading(true);
//            mLinearLayout.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//            if (mSearchAdapter.getItemCount() == 0){
//                mLinearLayout.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
//            }
        }
    };
}
