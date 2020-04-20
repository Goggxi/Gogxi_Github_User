package com.gogxi.githubusers.data.source.remote;

import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.model.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("/search/users")
    @Headers("Authentication: token <4bbba7ffce1b5819d4f45d92def0799a725f9301>")
    Call<UsersResponse> getUser(@Query("q") String username);

    @GET("/users/{username}")
    @Headers("Authentication: token <4bbba7ffce1b5819d4f45d92def0799a725f9301>")
    Call<Users> getDetailUser(@Path("username") String username);

//    @GET("/users/{username}/followers")
//    Call<List<Users>> getFollowersUser(@Path("username") String username);
//
//    @GET("/users/{username}/following")
//    Call<List<Users>> getFollowingUser(@Path("username") String username);

//    @GET("search/users?q={username}")
//    Call<UsersResponse> searchUser(@Path("username")String username);

//    @Headers("Authentication: token <Personal Access Token>")

//    @GET("search/users")
//    Call<UsersResponse> searchUser(@Query("q") String query);

//    @GET("users/{name}")
//    Call<UserDetailData> getUserDetail(@Path("name") String name);
//
//    @GET("users/{name}/followers")
//    Call<List<UserData>> getFollowers(@Path("name") String name);
}
