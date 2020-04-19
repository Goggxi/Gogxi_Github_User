package com.gogxi.githubusers.ui.search;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.model.UsersResponse;
import com.gogxi.githubusers.data.source.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchVM extends ViewModel {
    private ApiClient client;
    private MutableLiveData<List<Users>> mUsers = new MutableLiveData<>();

    public void setResultUsers(String username, Context context){
        if (this.client == null){
            client = new ApiClient();
        }
        //noinspection NullableProblems
        client.getClient()
                .getUser(username)
                .enqueue(new Callback<UsersResponse>() {

            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                Log.d("Message", "onResponse: " + response.body());
                UsersResponse mUsersResponse = response.body();
                try {
                    if (mUsersResponse != null && mUsersResponse.getItems() != null){
                        List<Users> users = mUsersResponse.getItems();
                        mUsers.postValue(users);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Log.d("Message", "onFailure: " +t.getMessage());
            }
        });
    }

    public LiveData<List<Users>> getResultUsers(){
        return mUsers;
    }
}
