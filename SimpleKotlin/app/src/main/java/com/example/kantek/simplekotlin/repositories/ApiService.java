package com.example.kantek.simplekotlin.repositories;

import com.android.support.kotlin.core.network.ApiPageResponse;
import com.android.support.kotlin.core.network.ApiResponse;
import com.example.kantek.simplekotlin.models.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<User>> getUser(@Path("id") int id);

    @GET("users?page=2")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<List<User>>> getUsers();

    @GET("users?page=1&per_page=20")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<List<User>>> getCachedUsers();

    @GET("users")
    @Headers({"Content-Type: application/json"})
    Call<ApiPageResponse<User>> getPageUsers(@Query("page") Integer page, @Query("per_page") int pageSize);
}
