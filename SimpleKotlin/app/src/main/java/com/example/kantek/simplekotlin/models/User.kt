package com.example.kantek.simplekotlin.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class User {

    @PrimaryKey
    @SerializedName("id")
    var id = 0

    @SerializedName("first_name")
    var firstName = ""

    @SerializedName("last_name")
    var lastName = ""

    @SerializedName("avatar")
    var avatar = ""

    override fun toString() = "$id - $firstName $lastName - $avatar"
}
