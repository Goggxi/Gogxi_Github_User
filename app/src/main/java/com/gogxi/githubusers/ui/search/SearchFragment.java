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

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    private SearchView searchView;

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

        getResultSearch();
        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.app_name);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        mLinearLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        implementSearch(menu);
    }

    private void implementSearch(final Menu menu) {
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        if (searchManager != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);

            searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener(){
                @Override
                public boolean onMenuItemActionExpand(MenuItem item){
                    searchView.setQueryHint("Search User");
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            Log.i( "onQueryTextSubmit: ", query);
                            if (query.isEmpty()){
                                showLoading(true);
                                mLinearLayout.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                return true;
                            }
                            mSearchVM.setResultUsers(query);
                            showLoading(false);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            Log.i("onQueryTextChange: ", newText);
                            if (newText.isEmpty()){
                                showLoading(true);
                                mLinearLayout.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                return true;
                            }
                            mSearchVM.setResultUsers(newText);
                            showLoading(false);
                            return true;
                        }
                    });
                    new Handler().post(() -> {
                        searchView.requestFocus();
                        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(searchView.findFocus(), 0);
                        }
                    });
//                    Toast.makeText(getActivity(), "Search Open", Toast.LENGTH_LONG).show();
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item){
//                    Toast.makeText(getActivity(), "Search Close", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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

    private Observer<List<Users>> getUser = users -> {
        if (users != null){
            mSearchAdapter.notifyDataSetChanged();
            mSearchAdapter.setUsers(users);
            showLoading(true);
            mLinearLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (mSearchAdapter.getItemCount() == 0){
                mLinearLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
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
