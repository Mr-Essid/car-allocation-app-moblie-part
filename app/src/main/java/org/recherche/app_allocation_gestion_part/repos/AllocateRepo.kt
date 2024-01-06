package org.recherche.app_allocation_gestion_part.repos

import android.os.Build
import androidx.annotation.RequiresApi
import org.recherche.app_allocation_gestion_part.models.Details
import org.recherche.app_allocation_gestion_part.models.HistoryRequest
import org.recherche.app_allocation_gestion_part.models.HistoryResponse
import org.recherche.app_allocation_gestion_part.network.netObject
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class AllocateRepo {

    val allocationApi = netObject.getAllocationAPI()


    suspend fun getAllHistory(token: String): Response<List<HistoryResponse>> {
        return allocationApi.getAllActiveAllocation(token)
    }



    suspend fun getHistoryById(token: String, id: Int): Response<HistoryResponse> {
        return allocationApi.historyById(token, id)
    }


    suspend fun getBackHistory(token: String) : Response<List<HistoryResponse>>{
        return allocationApi.blackHistory(token)
    }


    suspend fun allocate(token: String, allocationRequest: HistoryRequest): Response<Details> {
        return allocationApi.allocate(token, allocationRequest)
    }


    suspend fun unAllocate(token: String, id: Int): Response<Details> {
        return allocationApi.unAllocate(token, id)

    }


    suspend fun track(token: String, id: Int) : Response<Details>{
        return allocationApi.addToTrack(token, id)
    }

    suspend fun removeTrack(token: String, id: Int) : Response<Details>{
        return allocationApi.removeFromTrack(token, id)
    }


}