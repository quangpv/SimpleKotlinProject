package com.example.kantek.simplekotlin

import android.app.Application
import com.android.example.github.di.TLSSocketFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {


    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .client(createSSLSafeClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    private fun createSSLSafeClient() = OkHttpClient.Builder()
            .sslSocketFactory(TLSSocketFactory())
            .build()

    companion object {
        private lateinit var sInstance: MyApplication
        val instance: MyApplication
            get() = sInstance
    }
}