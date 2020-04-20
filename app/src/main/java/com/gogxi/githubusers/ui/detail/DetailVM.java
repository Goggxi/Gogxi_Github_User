package com.gogxi.githubusers.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gogxi.githubusers.data.model.UsersDetail;
import com.gogxi.githubusers.data.source.remote.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVM extends ViewModel {
//    private ApiClient client;
//    private MutableLiveData<List<UsersDetail>> mUsers = new MutableLiveData<>();
//
//    public void setDetailUsers(String username){
//        if (this.client == null){
//            client = new ApiClient();
//        }
//        //noinspection NullableProblems
//        client.getClient()
//                .getDetailUser(username)
//                .enqueue(new Callback<UsersDetail>() {
//                    @Override
//                    public void onResponse(Call<UsersDetail> call, Response<UsersDetail> response) {
//                        Log.d("Message", "onResponse: " + response.body());
//                        UsersDetail mUsersDetail = response.body();
//                        try {
//                            if (mUsersDetail != null && mUsersDetail.getName() != null){
//                                List<UsersDetail> users = new ArrayList<>();
//                                mUsers.postValue(users);
//                            }
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UsersDetail> call, Throwable t) {
//                        Log.d("Message", "onFailure: " +t.getMessage());
//                    }
//                });
//    }
//
//    public LiveData<List<UsersDetail>> getDetailUsers(){
//        return mUsers;
//    }
}
