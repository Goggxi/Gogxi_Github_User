package com.gogxi.githubusers.ui.detail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.detail.followers.FollowersFragment;
import com.gogxi.githubusers.ui.detail.following.FollowingFragment;

import java.util.Objects;

public class DetailPageAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following,
    };

    public DetailPageAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowersFragment();
                break;
            case 1:
                fragment = new FollowingFragment();
                break;
        }
        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
