package org.recherche.app_allocation_gestion_part.network

import org.recherche.app_allocation_gestion_part.models.Details
import org.recherche.app_allocation_gestion_part.models.HistoryRequest
import org.recherche.app_allocation_gestion_part.models.HistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HistoryAPI {

    @POST("/allocate")
    suspend fun allocate(@Header("Authorization") token: String, @Body allocationRequest: HistoryRequest): Response<Details>


    @PUT("/allocate")
    suspend fun unAllocate(@Header("Authorization") token: String, @Query("id_") id: Int): Response<Details>

    @GET("/allocate")
    suspend fun getAllHistory(@Header("Authorization") token: String): Response<List<HistoryResponse>>

    @GET("/allocate/active/all")
    suspend fun getAllActiveAllocation(@Header("Authorization") token: String): Response<List<HistoryResponse>>




    @GET("/allocate/{id_}")
    suspend fun historyById(@Header("Authorization") token: String, @Path("id_") id: Int): Response<HistoryResponse>


    @GET("/allocate/black/all")
    suspend fun blackHistory(@Header("Authorization") token: String): Response<List<HistoryResponse>>


    @PUT("/allocate/track/{id_}")
    suspend fun addToTrack(@Header("Authorization") token: String, @Path("id_") id: Int): Response<Details>


    @PUT("/allocate/untrack/{id_}")
    suspend fun removeFromTrack(@Header("Authorization") token: String, @Path("id_") id: Int): Response<Details>



}