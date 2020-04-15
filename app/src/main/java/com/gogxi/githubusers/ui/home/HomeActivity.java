package com.gogxi.githubusers.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.favorite.FavoriteFragment;
import com.gogxi.githubusers.ui.search.SearchFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
  //  private ChipNavigationBar bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        bottomNav = findViewById(R.id.bottom_navigation);
//
        if (savedInstanceState==null){
            mFragmentManager = getSupportFragmentManager();
            SearchFragment mSearchFragment = new SearchFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mSearchFragment)
                    .commit();
        }
//
//        menuChip();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                FavoriteFragment fragment = new FavoriteFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                break;
            case R.id.settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



//    private void menuChip(){
//        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int id) {
//                Fragment fragment = null;
//                switch (id){
//                    case R.id.home:
//                        fragment = new SearchFragment();
//                        break;
//                    case R.id.favorite :
//                        fragment = new FavoriteFragment();
//                        break;
//                }
//
//                if (fragment != null) {
//                    mFragmentManager = getSupportFragmentManager();
//                    mFragmentManager.beginTransaction()
//                            .replace(R.id.fragment_container, fragment)
//                            .commit();
//                }
//            }
//        });
//    }
}
