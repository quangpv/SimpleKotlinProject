package com.example.kantek.simplekotlin.components.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.kantek.simplekotlin.ApiService
import com.example.kantek.simplekotlin.AppDatabase
import com.example.kantek.simplekotlin.BuildConfig
import com.example.kantek.simplekotlin.MyApplication
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: MyApplication) =
            application.applicationContext

    @Provides
    @Singleton
    fun provideApiService(retrofitBuilder: Retrofit.Builder) =
            retrofitBuilder.baseUrl("https://reqres.in/api/")
                    .build()
                    .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSharePreference(context: Context) =
            context.getSharedPreferences(BuildConfig.CACHE, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDatabase(context: Context) =
            Room.databaseBuilder(context,AppDatabase::class.java,"app_database")
                    .build()

}
