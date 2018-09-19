package com.example.kantek.simplekotlin.repositories

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kantek.simplekotlin.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}