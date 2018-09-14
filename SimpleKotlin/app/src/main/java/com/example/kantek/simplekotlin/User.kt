package com.example.kantek.simplekotlin

import com.google.gson.annotations.SerializedName

class User {
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
