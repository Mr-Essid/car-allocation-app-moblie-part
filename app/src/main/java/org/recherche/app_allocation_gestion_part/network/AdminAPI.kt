package org.recherche.app_allocation_gestion_part.network

import org.recherche.app_allocation_gestion_part.models.AdminRequest
import org.recherche.app_allocation_gestion_part.models.AdminResponse
import org.recherche.app_allocation_gestion_part.models.CountResResponse
import org.recherche.app_allocation_gestion_part.models.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AdminAPI {


    @FormUrlEncoded
    @POST("/admins/login")
    suspend fun loginAdmin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<TokenResponse>

    @POST("/admins/")
    suspend fun signUp(@Body adminRequest: AdminRequest): Response<AdminResponse>

    @GET("/admins/get/current")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<AdminResponse>

    @GET("/admins/res/count-res")
    suspend fun getCountAll(@Header("Authorization") token: String): Response<CountResResponse>



}