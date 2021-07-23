package com.example.meditation4u.UserApi

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @SerializedName("success") val isSuccessful: String,
    @SerializedName("data") val someData: List<QuotesList>
)

data class QuotesList(
    @SerializedName("id")
    val quotesId: Int,
    @SerializedName("title")
    val quotesTitle: String,
    @SerializedName("image")
    val quotesImage: String,
    @SerializedName("description")
    val quotesDescription: String
)



