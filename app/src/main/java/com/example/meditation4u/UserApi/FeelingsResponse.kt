package com.example.meditation4u.UserApi

import com.google.gson.annotations.SerializedName

data class FeelingsResponse(
    @SerializedName("success") val isSuccessful: String,
    @SerializedName("data") val someData: List<Feelings>
)

data class Feelings(
    @SerializedName("id")
    val feelingsId: Int,
    @SerializedName("title")
    val feelingsTitle: String,
    @SerializedName("image")
    val feelingsImage: String,
    @SerializedName("position")
    val feelingsPosition: Int
)