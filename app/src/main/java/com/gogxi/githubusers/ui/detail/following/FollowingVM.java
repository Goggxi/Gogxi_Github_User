package com.gogxi.githubusers.ui.detail.following;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.source.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingVM extends ViewModel {
    private ApiClient client;
    private MutableLiveData<List<Users>> mUsers = new MutableLiveData<>();

    public void setFollowingUsers(String username){
        if (this.client == null){
            client = new ApiClient();
        }
        //noinspection NullableProblems
        client.getClient()
                .getFollowingUser(username)
                .enqueue(new Callback<List<Users>>() {

            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                Log.d("Message", "onResponse: " + response.body());
                List<Users> users;
                users = response.body();
                try {
                    if (users != null){
                        mUsers.postValue(users);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.d("Message", "onFailure: " +t.getMessage());
            }
        });
    }

    public LiveData<List<Users>> getFollowingUsers(){
        return mUsers;
    }
}
