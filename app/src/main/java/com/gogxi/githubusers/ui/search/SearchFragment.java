package com.gogxi.githubusers.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.ui.favorite.FavoriteFragment;
import com.gogxi.githubusers.ui.home.HomeActivity;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private SearchAdapter mSearchAdapter = new SearchAdapter();
    private SearchVM mSearchVM;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;
    private ProgressBar mProgressBar;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_search);
        mLinearLayout = view.findViewById(R.id.no_result);
        mProgressBar = view.findViewById(R.id.progress_search);

        showLoading(false);
        getResultSearch();
        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.app_name);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW| MenuItem.SHOW_AS_ACTION_IF_ROOM );

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i( "onQueryTextSubmit: ", query);
                mSearchVM.setResultUsers(query, getContext());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("onQueryTextChange: ", newText);
//                mRecyclerView.setAdapter(mSearchAdapter);
                mSearchVM.setResultUsers(newText, getContext());
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                return false;
            case R.id.favorite:
                if (getFragmentManager() != null){
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new FavoriteFragment(), FavoriteFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
                break;
            case R.id.settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getResultSearch(){
        mSearchVM = new ViewModelProvider(this).get(SearchVM.class);
//        mSearchVM.setResultUsers(getString(R.string.language));
        mSearchVM.getResultUsers().observe(this,getUser);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    private Observer<List<Users>> getUser = new Observer<List<Users>>() {
        @Override
        public void onChanged(List<Users> users) {
            if (users != null){
                mSearchAdapter.setUsers(users);
                mSearchAdapter.notifyDataSetChanged();
                showLoading(true);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state){
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
