package com.example.kantek.simplekotlin

class User {
    private var id = 0
    private var first_name = ""
    private var last_name = ""
    private var avatar = ""
    override fun toString() = "$id - $first_name $last_name - $avatar"
}
