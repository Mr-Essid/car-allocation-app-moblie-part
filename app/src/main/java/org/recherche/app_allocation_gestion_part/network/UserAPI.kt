package org.recherche.app_allocation_gestion_part.network

import org.recherche.app_allocation_gestion_part.models.UserRequest
import org.recherche.app_allocation_gestion_part.models.UserResponse
import org.recherche.app_allocation_gestion_part.models.UserUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @GET("/users/get/all")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<List<UserResponse>>

    @GET("/users/{id_}")
    suspend fun getUserById(
        @Path("id_") id_: Int,
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @GET("/users/all/banned")
    suspend fun getAllBannedUsers(@Header("Authorization") token: String): Response<List<UserResponse>>

    @GET("/users/all/active")
    suspend fun getAllActiveUsers(@Header("Authorization") token: String): Response<List<UserResponse>>

    @GET("/users/all/name/{name}")
    suspend fun getAllUsersByName(
        @Path("name") name: String,
        @Header("Authorization") token: String
    ): Response<List<UserResponse>>

    @GET("/users/all/search/{name}")
    suspend fun searchUsersByName(
        @Path("name") name: String,
        @Header("Authorization") token: String
    )


    @POST("/users")
    suspend fun addUser(
        @Body user: UserRequest,
        @Header("Authorization") token: String
    ): Response<UserResponse>


    @POST("/users/banne/{id}")
    suspend fun banneUser(
        @Path("id") id_: Int,
        @Header("Authorization") token: String
    ): Response<UserResponse>


    @POST("/users/de-banne/{id}")
    suspend fun debanneUser(
        @Path("id") id_: Int,
        @Header("Authorization") token: String
    ): Response<UserResponse>


    @PUT("/users")
    suspend fun update(
         @Body userUpdate: UserUpdate,
         @Header("Authorization") token: String,
    ): Response<UserResponse>


}


