package com.example.kantek.simplekotlin;

import com.android.support.kotlin.core.network.ApiResponse;


import org.jetbrains.annotations.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<User>> getUser(@Path("id") int id);

    @GET("users?page=2")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<List<User>>> getUsers();
}
