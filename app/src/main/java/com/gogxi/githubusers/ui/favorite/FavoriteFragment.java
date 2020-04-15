package com.gogxi.githubusers.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.home.HomeActivity;
import com.gogxi.githubusers.ui.search.SearchFragment;

import java.util.Objects;


public class FavoriteFragment extends Fragment {
    private FragmentManager mFragmentManager;

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

        this.setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.favorite);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

}
