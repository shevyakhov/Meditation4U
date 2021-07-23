package com.example.meditation4u.UserApi

import com.google.gson.annotations.SerializedName

data class PingResponse(
    @SerializedName("success") val isSuccessful: String,
    @SerializedName("data") val someData: String
)