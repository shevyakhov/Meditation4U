package com.example.meditation4u.UserApi

import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("./ping")
    fun getPing(): Single<PingResponse>

    @GET("./quotes")
    fun getQuotes(): Single<QuotesResponse>

    @GET("./feelings")
    fun getFeelings(): Single<FeelingsResponse>

}