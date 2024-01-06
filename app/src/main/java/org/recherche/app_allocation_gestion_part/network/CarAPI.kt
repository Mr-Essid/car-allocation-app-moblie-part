package org.recherche.app_allocation_gestion_part.network

import org.recherche.app_allocation_gestion_part.models.CarRequest
import org.recherche.app_allocation_gestion_part.models.CarResponse
import org.recherche.app_allocation_gestion_part.models.CarUpdate
import org.recherche.app_allocation_gestion_part.models.Details
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CarAPI {


    @POST("/cars")
    suspend fun addCar(
        @Header("Authorization") token: String,
        @Body carRequest: CarRequest
    ): Response<CarResponse>


    @PUT("/cars")
    suspend fun updateCar(
        @Header("Authorization") token: String,
        @Body carUpdate: CarUpdate
    ): Response<CarResponse>

    // for now delete not available
    @DELETE("/cars")
    suspend fun deleteCar(
        @Header("Authorization") token: String,
        @Query("id_") id: Int
        ): Response<CarResponse>


    @GET("/cars")
    suspend fun getAllCars(
        @Header("Authorization") token: String,
        @Query("id_") id: Int? = null
    ): Response<List<CarResponse>>


    @GET("/cars/active")
    suspend fun getAllActiveCars(
        @Header("Authorization") token: String,
    ): Response<List<CarResponse>>



    @GET("/cars/available")
    suspend fun getAvailableCars(
        @Header("Authorization") token: String,
        @Query("model") model: String? = null,
        @Query("color") color: String? = null
    ): Response<List<CarResponse>>


    @POST("/cars/active_/{id_}")
    suspend fun activeCar(
        @Header("Authorization") token: String,
        @Path("id_") id: Int
    ): Response<Details>


    @POST("/cars/dis-active/{id_}")
    suspend fun disActiveCar(
        @Header("Authorization") token: String,
        @Path("id_") id: Int
    ): Response<Details>


    @GET("/cars/models")
    suspend fun models(
        @Header("Authorization") token: String,
        ): Response<List<String>>


}