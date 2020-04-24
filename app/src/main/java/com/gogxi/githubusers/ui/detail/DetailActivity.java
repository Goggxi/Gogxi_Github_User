package com.gogxi.githubusers.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
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

public class DetailActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_USER_FAVORITE = "extra_user_favorite";
    private static final String BASE_IMAGE_URL = "https://avatars1.githubusercontent.com/u/" ;
    public static final String EXTRA_POSITION = "extra_position";

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 201;

    private final int ALERT_DIALOG_DELETE = 20;

    private Users user;
    private FavoriteEntity mFavoriteEntity;
    private DetailVM mDetailVM;

    private String actionBarTitle;
    private int position;
    private boolean isDelete = false;

    private ApiClient client;
    private CircleImageView mAvatarProfile;
    private Button
            mButtonAddFavorite,
            mButtonDeleteFavorite;
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

        user = getIntent().getParcelableExtra(EXTRA_USER);
        mFavoriteEntity = getIntent().getParcelableExtra(EXTRA_USER_FAVORITE);

        mDetailVM = obtainViewModel(DetailActivity.this);

        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(this, getSupportFragmentManager(), user, mFavoriteEntity);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailPageAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
        mButtonAddFavorite = findViewById(R.id.btn_add_favorite);
        mButtonDeleteFavorite = findViewById(R.id.btn_delete_favorite);

        mImageViewName = findViewById(R.id.imageViewName);
        mImageViewCompany = findViewById(R.id.imageViewCompany);
        mImageViewBlog = findViewById(R.id.imageViewBlog);
        mImageViewLocation = findViewById(R.id.imageViewLocation);

        if (user != null){
            getDetailUsers(user.getLogin());
            final int id = user.getId();
            Log.d("GET DAO", "id user: " + id);
            mDetailVM.getCount(id).observe(this, integer -> {
                if (integer != 0){
                    mButtonAddFavorite.setVisibility(View.GONE);
                    mButtonDeleteFavorite.setVisibility(View.VISIBLE);
                    Log.d("data DITAMBAH", "id user: " + integer);
                }
                else {
                    mButtonAddFavorite.setVisibility(View.VISIBLE);
                    mButtonDeleteFavorite.setVisibility(View.GONE);
                    Log.d("data DIHAPUS", "id user: " + integer);
                }
            });
        }

        if (mFavoriteEntity != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isDelete = true;
        } else {
            mFavoriteEntity = new FavoriteEntity();
        }

        if (isDelete) {
            if (mFavoriteEntity != null) {
                if (mFavoriteEntity.getName().equals("")){
                    mImageViewName.setVisibility(View.GONE);
                    mNameProfile.setVisibility(View.GONE);
                }
                if (mFavoriteEntity.getCompany().equals("")){
                    mImageViewCompany.setVisibility(View.GONE);
                    mCompanyProfile.setVisibility(View.GONE);
                }
                if (mFavoriteEntity.getBlog().equals("")){
                    mImageViewBlog.setVisibility(View.GONE);
                    mBlogProfile.setVisibility(View.GONE);
                }
                if (mFavoriteEntity.getLocation().equals("")){
                    mImageViewLocation.setVisibility(View.GONE);
                    mLocationProfile.setVisibility(View.GONE);
                }

                mIdUserProfile.setText(String.valueOf(mFavoriteEntity.getUser_id()));
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

                mButtonAddFavorite.setVisibility(View.GONE);
                mButtonDeleteFavorite.setVisibility(View.VISIBLE);
                actionBarTitle = mLoginProfile.getText().toString();
            }
        }

        if (getSupportActionBar() != null ){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mButtonAddFavorite.setOnClickListener(this);
        mButtonDeleteFavorite.setOnClickListener(this);
    }

    @NonNull
    private static DetailVM obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(DetailVM.class);
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

    private void insertDB(){
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
            Toast.makeText(getApplicationContext(), R.string.not_found , Toast.LENGTH_LONG).show();
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

            mDetailVM.insert(mFavoriteEntity);
            setResult(RESULT_ADD, intent);

            mButtonDeleteFavorite.setVisibility(View.VISIBLE);
            mButtonAddFavorite.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), R.string.add_to_favorite , Toast.LENGTH_LONG).show();
        }
    }

    private void deleteDB(){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_USER_FAVORITE, mFavoriteEntity);

        mDetailVM.delete(mFavoriteEntity);
        setResult(RESULT_DELETE, intent);

        Toast.makeText(getApplicationContext(), R.string.delete_to_favorite , Toast.LENGTH_LONG).show();
        mButtonDeleteFavorite.setVisibility(View.GONE);
        mButtonAddFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_favorite :
                insertDB();
                break;
            case R.id.btn_delete_favorite:
                if (isDelete){
                    showAlertDialog(ALERT_DIALOG_DELETE);
                } else {
                    int ALERT_DIALOG_DELETE_API = 30;
                    showAlertDialog(ALERT_DIALOG_DELETE_API);
                }
                break;
        }
    }

    private void showAlertDialog(int type) {
        final boolean isDialogDelete = type == ALERT_DIALOG_DELETE;
        String dialogTitle, dialogMessage;

        dialogTitle = getString(R.string.message_delete);
        dialogMessage = getString(R.string.delete_to_favorite);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    if (isDialogDelete) {
                        deleteDB();
                    } else {
                        final String login = user.getLogin();
                        mDetailVM.deleteByLogin(login);
                        mButtonDeleteFavorite.setVisibility(View.VISIBLE);
                        mButtonAddFavorite.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), R.string.delete_to_favorite , Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
