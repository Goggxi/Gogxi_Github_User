package com.gogxi.githubusers.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogxi.githubusers.R;
import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.model.UsersDetail;
import com.gogxi.githubusers.data.source.local.FavoriteEntity;
import com.gogxi.githubusers.data.source.remote.ApiClient;
import com.gogxi.githubusers.viewmodel.ViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_USER_FAVORITE = "extra_user_favorite";
    private static final String BASE_IMAGE_URL = "https://avatars1.githubusercontent.com/u/" ;
    public static final String EXTRA_POSITION = "extra_position";

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 201;

    private FavoriteEntity mFavoriteEntity;
    private DetailVM mDetailVM;

    private String actionBarTitle;
    private String btnTitle;
    private int position;
    private boolean isDelete = false;

    private ApiClient client;
    private CircleImageView mAvatarProfile;
    private Button mButtonFavorite;
    private TextView mIdUserProfile,
            mLoginProfile,
            mReposProfile,
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

        mDetailVM = obtainViewModel(DetailActivity.this);

        Users user = getIntent().getParcelableExtra(EXTRA_USER);

        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(this, getSupportFragmentManager(), user);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailPageAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_movie);
//        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_tv_show);
//        -----------------------------------------------------------------------------------------------------
        mAvatarProfile = findViewById(R.id.crclvw_avatar_profile);
        mLoginProfile = findViewById(R.id.txtvw_login_profile);
        mIdUserProfile = findViewById(R.id.txtvw_id_user_profile);
        mReposProfile = findViewById(R.id.txtvw_repos_profile);
        mFollowersProfile = findViewById(R.id.txtvw_followers_profile);
        mFollowingProfile = findViewById(R.id.txtvw_following_profile);
        mNameProfile = findViewById(R.id.txtvw_name_profile);
        mCompanyProfile = findViewById(R.id.txtvw_company_profile);
        mBlogProfile = findViewById(R.id.txtvw_blog_profile);
        mLocationProfile = findViewById(R.id.txtvw_location_profile);
        mButtonFavorite = findViewById(R.id.btn_favorite);

        mImageViewName = findViewById(R.id.imageViewName);
        mImageViewCompany = findViewById(R.id.imageViewCompany);
        mImageViewBlog = findViewById(R.id.imageViewBlog);
        mImageViewLocation = findViewById(R.id.imageViewLocation);

        if (user != null){
            getDetailUsers(user.getLogin());
        }

        mFavoriteEntity = getIntent().getParcelableExtra(EXTRA_USER_FAVORITE);
        if (mFavoriteEntity != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isDelete = true;
        } else {
            mFavoriteEntity = new FavoriteEntity();
        }

        if (isDelete) {
            actionBarTitle = getString(R.string.detail);
            btnTitle = getString(R.string.delete_to_favorite);
            mButtonFavorite.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mButtonFavorite.setBackground(getResources().getDrawable(R.drawable.bg_button_delete_favorite));

            if (mFavoriteEntity != null) {
                mIdUserProfile.setText(String.valueOf(mFavoriteEntity.getId()));
                mLoginProfile.setText(mFavoriteEntity.getLogin());
                mReposProfile.setText(String.valueOf(mFavoriteEntity.getPublicRepos()));
                mFollowersProfile.setText(String.valueOf(mFavoriteEntity.getFollowers()));
                mFollowingProfile.setText(String.valueOf(mFavoriteEntity.getFollowing()));
                mNameProfile.setText(mFavoriteEntity.getName());
                mCompanyProfile.setText(mFavoriteEntity.getCompany());
                mBlogProfile.setText(mFavoriteEntity.getBlog());
                mLocationProfile.setText(mFavoriteEntity.getLocation());
                Glide.with(getApplicationContext())
                        .load(BASE_IMAGE_URL + mFavoriteEntity.getUser_id())
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                        .into(mAvatarProfile);
            }
        } else {
            actionBarTitle = getString(R.string.detail);
            btnTitle = getString(R.string.add_to_favorite);
        }

        mButtonFavorite.setText(btnTitle);
        mButtonFavorite.setOnClickListener(v -> {
//            Toast.makeText(getApplicationContext(), "You clicked " + mLocationProfile.getText() , Toast.LENGTH_LONG).show();
            actionDB();
        });

        if (getSupportActionBar() != null ){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    private static DetailVM obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(DetailVM.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                                    mIdUserProfile.setText(String.valueOf(mUsersDetail.getId()));
                                    mLoginProfile.setText(mUsersDetail.getLogin());
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
                            Objects.requireNonNull(getSupportActionBar()).setTitle(mLoginProfile.getText());
                            Log.d("Message", "id user: " + mIdUserProfile.getText());
//                            Toast.makeText(getApplicationContext(), "You clicked " + mIdUserProfile.getText(), Toast.LENGTH_LONG).show();
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

    private void actionDB(){
        int id_user = Integer.parseInt(mIdUserProfile.getText().toString());
        int repo = Integer.parseInt(mReposProfile.getText().toString());
        int followers = Integer.parseInt(mFollowersProfile.getText().toString());
        int following = Integer.parseInt(mFollowingProfile.getText().toString());
        String login = mLoginProfile.getText().toString().trim();
        String name = mNameProfile.getText().toString().trim();
        String company = mCompanyProfile.getText().toString().trim();
        String blog = mBlogProfile.getText().toString().trim();
        String location = mLocationProfile.getText().toString().trim();

        if (mIdUserProfile == null) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
        } else {
            mFavoriteEntity.setUser_id(id_user);
            mFavoriteEntity.setPublicRepos(repo);
            mFavoriteEntity.setFollowers(followers);
            mFavoriteEntity.setFollowing(following);
            mFavoriteEntity.setLogin(login);
            mFavoriteEntity.setName(name);
            mFavoriteEntity.setCompany(company);
            mFavoriteEntity.setBlog(blog);
            mFavoriteEntity.setLocation(location);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_USER_FAVORITE, mFavoriteEntity);
            intent.putExtra(EXTRA_POSITION, position);

            if (isDelete) {
                mDetailVM.delete(mFavoriteEntity);
//                intent.putExtra(EXTRA_POSITION, position);
                setResult(RESULT_DELETE, intent);
                Toast.makeText(getApplicationContext(), "Berhasil Dihapus", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
                mDetailVM.insert(mFavoriteEntity);
                setResult(RESULT_ADD, intent);
            }
        }
    }
}
