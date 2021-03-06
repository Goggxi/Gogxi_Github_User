package com.gogxi.githubusers.data.source.remote;

import com.gogxi.githubusers.data.model.Users;
import com.gogxi.githubusers.data.model.UsersDetail;
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
    Call<UsersDetail> getDetailUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    @Headers("Authentication: token <4bbba7ffce1b5819d4f45d92def0799a725f9301>")
    Call<List<Users>> getFollowersUser(@Path("username") String username);

    @GET("/users/{username}/following")
    @Headers("Authentication: token <4bbba7ffce1b5819d4f45d92def0799a725f9301>")
    Call<List<Users>> getFollowingUser(@Path("username") String username);
}
