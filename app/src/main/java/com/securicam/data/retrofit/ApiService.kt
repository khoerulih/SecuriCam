package com.securicam.data.retrofit

import com.securicam.data.responses.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("user/all")
    fun getAllUsers(
        @Header("Authorization") token: String,
    ) : Call<SearchCameraResponse>

    @GET("user/connections")
    fun getUsers(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String
    ) : Call<CameraConnectionResponse>


    @GET("user/search")
    fun search(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Query("email")  username: String
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

    @GET("user/connections")
    fun getAllCameraConnections(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
    ): Call<CameraConnectionResponse>

    @FormUrlEncoded
    @POST("user/connection/delete")
    fun deleteConnection(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Field("connectionId") connectionId: String,
    ): Call<DeleteConnectionResponse>

    @GET("notification/all")
    fun getAllNotification(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
    ): Call<NotificationResponse>


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

    @POST("pair/reject")
    fun rejectPairRequest(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Query("id") pairId: String,
    ): Call<RejectPairRequestResponse>

    @POST("pair/send")
    fun sendPairRequest(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Query("reciever") receiver: String,
    ): Call<SendPairResponse>

    @Multipart
    @POST("ai/predict")
    fun sendImageForPredictRequest(
        @Header("Authorization") token: String,
        @Header("x-access-token") accessToken: String,
        @Part image: MultipartBody.Part,
    ): Call<PredictResponse>
}