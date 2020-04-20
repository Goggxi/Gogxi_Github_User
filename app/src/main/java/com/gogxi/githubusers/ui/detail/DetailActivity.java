package com.gogxi.githubusers.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.model.UsersDetail;
import com.gogxi.githubusers.data.source.remote.ApiClient;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    private ApiClient client;
    private CircleImageView mAvatarProfile;
    private TextView mReposProfile,
            mFollowersProfile,
            mFollowingProfile,
            mNameProfile,
            mCompanyProfile,
            mBlogProfile,
            mLocationProfile;
    private ImageView mImageViewName,
            mImageViewCompany,
            mImageViewBlog,
            mImageViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailPageAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_movie);
//        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_tv_show);
//        -----------------------------------------------------------------------------------------------------
        mAvatarProfile = findViewById(R.id.crclvw_avatar_profile);
        mReposProfile = findViewById(R.id.txtvw_repos_profile);
        mFollowersProfile = findViewById(R.id.txtvw_followers_profile);
        mFollowingProfile = findViewById(R.id.txtvw_following_profile);
        mNameProfile = findViewById(R.id.txtvw_name_profile);
        mCompanyProfile = findViewById(R.id.txtvw_company_profile);
        mBlogProfile = findViewById(R.id.txtvw_blog_profile);
        mLocationProfile = findViewById(R.id.txtvw_location_profile);

        mImageViewName = findViewById(R.id.imageViewName);
        mImageViewCompany = findViewById(R.id.imageViewCompany);
        mImageViewBlog = findViewById(R.id.imageViewBlog);
        mImageViewLocation = findViewById(R.id.imageViewLocation);

        Users user = getIntent().getParcelableExtra(EXTRA_USER);
        if (user != null){
            getDetailUsers(user.getLogin());
        }
    }

    public void getDetailUsers(String username){
        if (this.client == null){
            client = new ApiClient();
        }
        try {
            //noinspection NullableProblems
            client.getClient()
                    .getDetailUser(username)
                    .enqueue(new Callback<UsersDetail>() {
                        @Override
                        public void onResponse(Call<UsersDetail> call, Response<UsersDetail> response) {
                            Log.d("Message", "onResponse: " + response.body());
                            UsersDetail mUsersDetail = response.body();
                            try {
                                if (mUsersDetail != null){
                                    mReposProfile.setText(String.valueOf(mUsersDetail.getPublicRepos()));
                                    mFollowersProfile.setText(String.valueOf(mUsersDetail.getFollowers()));
                                    mFollowingProfile.setText(String.valueOf(mUsersDetail.getFollowing()));
                                    mNameProfile.setText(mUsersDetail.getName());
                                    mCompanyProfile.setText(mUsersDetail.getCompany());
                                    mBlogProfile.setText(mUsersDetail.getBlog());
                                    mLocationProfile.setText(mUsersDetail.getLocation());
                                    Glide.with(getApplicationContext())
                                            .load(mUsersDetail.getAvatarUrl())
                                            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                                            .into(mAvatarProfile);

                                    if (mUsersDetail.getName() == null){
                                        mImageViewName.setVisibility(View.GONE);
                                        mNameProfile.setVisibility(View.GONE);
                                    }
                                    if (mUsersDetail.getCompany() == null){
                                        mImageViewCompany.setVisibility(View.GONE);
                                        mCompanyProfile.setVisibility(View.GONE);
                                    }
                                    if (mUsersDetail.getBlog().equals("")){
                                        mImageViewBlog.setVisibility(View.GONE);
                                        mBlogProfile.setVisibility(View.GONE);
                                    }
                                    if (mUsersDetail.getLocation() == null){
                                        mImageViewLocation.setVisibility(View.GONE);
                                        mLocationProfile.setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
//                            Toast.makeText(getApplicationContext(), "You clicked " + mUsersDetail.getCompany(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<UsersDetail> call, Throwable t) {
                            Log.d("Message", "onFailure: " +t.getMessage());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
