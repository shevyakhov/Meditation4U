package com.example.meditation4u.UserApi

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @GET("./ping")
    fun getPing(): Single<PingResponse>

    @GET("./quotes")
    fun getQuotes(): Single<QuotesResponse>

    @GET("./feelings")
    fun getFeelings(): Single<FeelingsResponse>


    @POST("./user")
    fun createUser(
        @Body userRequest: UserRequest
    ): Call<CreateUserResponse>

    @POST("./user/login")
    fun getLogin(
        @Body userRequest:UserRequest
    ): Call<LoginResponse>

}