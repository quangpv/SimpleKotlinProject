package com.example.kantek.simplekotlin.repositories

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.kantek.simplekotlin.models.User

@Dao
interface UserDao {

    @Query("select * from User order by id asc")
    fun getUsers(): DataSource.Factory<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(result: List<User>?)
}