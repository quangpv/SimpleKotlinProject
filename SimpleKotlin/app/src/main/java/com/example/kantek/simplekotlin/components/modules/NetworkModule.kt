package com.example.kantek.simplekotlin.components.modules

import com.android.example.github.di.TLSSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.internal.platform.Platform
import com.example.kantek.simplekotlin.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): LoggingInterceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()


    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor): OkHttpClient {
        val tslFactory = TLSSocketFactory()
        return OkHttpClient.Builder()
                .sslSocketFactory(tslFactory, tslFactory.systemDefaultTrustManager())
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideGsonConvertFactory() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gsonConverterFactory: GsonConverterFactory,
                               okHttpClient: OkHttpClient) = Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
}
