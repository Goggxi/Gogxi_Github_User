package com.gogxi.githubusers.ui.search;

import android.app.SearchManager;
import android.content.Context;
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
    private SearchAdapter mSearchAdapter;
    private SearchVM mSearchVM;
//    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView = null;

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
//        mSearchView = view.findViewById(R.id.sv_user);
        mRecyclerView = view.findViewById(R.id.rv_search);
//        result();

        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.app_name);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);

//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        if (searchItem != null) {
//            mSearchView = (SearchView) searchItem.getActionView();
//        }
//        if (mSearchView != null) {
//            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//                    getResultSearch(newText);
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//                    getResultSearch(query);
//                    return true;
//                }
//            };
//            mSearchView.setOnQueryTextListener(queryTextListener);
//        }
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getResultSearch(newText);
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

//    private void result(){
//        mSearchView.setQueryHint("Search Users");
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                getResultSearch(query);
//                return false;
//            }
//        });
//    }

    private void getResultSearch(String username){
        mSearchVM = new ViewModelProvider(this).get(SearchVM.class);
        mSearchVM.setResultUsers(username, getContext());
//        mSearchVM.setResultUsers(getString(R.string.language));
        mSearchVM.getResultUsers().observe(this,getUser);
        mSearchAdapter = new SearchAdapter(getContext());
        mSearchAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    private Observer<List<Users>> getUser = new Observer<List<Users>>() {
        @Override
        public void onChanged(List<Users> users) {
            if (users != null){
                mSearchAdapter.setUsers(users);
            }
        }
    };
}
