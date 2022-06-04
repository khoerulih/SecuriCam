package com.securicam.data.retrofit

import com.securicam.data.responses.LoginData
import com.securicam.data.responses.LoginResponse
import com.securicam.data.responses.RegisterResponse
import com.securicam.data.responses.SearchCameraResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    fun getUsers() : Call<List<LoginData>>

    @FormUrlEncoded
    @GET("user/search")
    fun search(
        @Field("username") username: String
    ): Call<SearchCameraResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String,
    ): Call<RegisterResponse>

}