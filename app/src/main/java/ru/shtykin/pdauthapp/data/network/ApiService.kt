package ru.shtykin.pdauthapp.data.network

import retrofit2.Call
import retrofit2.http.*
import ru.shtykin.pdauthapp.data.network.model.ResponseCodeDto

interface ApiService {
    @GET("getCode/")
    fun getCode (
        @Query("login") login : String,
    ): Call<ResponseCodeDto>

    @GET("getToken/")
    fun getToken (
        @Query("login") login : String,
        @Query("password") password  : String,
    ): Call<String>

}