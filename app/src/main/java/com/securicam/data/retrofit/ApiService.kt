package com.securicam.data.retrofit

import com.securicam.data.responses.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
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

    @GET("user/connections")
    fun getAllCameraConnections(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
    ): Call<CameraConnectionResponse>

    @GET("pair/inbox")
    fun getPairingRequest(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
    ): Call<PairingRequestResponse>

    @POST("pair/accept")
    fun acceptPairRequest(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Query("id") pairId: String,
    ): Call<AcceptPairRequestResponse>
}