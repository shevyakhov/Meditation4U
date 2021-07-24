package com.example.meditation4u.UserApi

data class LoginResponse(
    val id: String,
    val email: String,
    val nickName: String,
    val avatar: String,
    val token: String
)
