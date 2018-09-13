package com.example.kantek.simplekotlin;

import com.android.support.kotlin.core.network.ApiResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ApiResponse<User>> getUser(@Path("id") int id);
}
