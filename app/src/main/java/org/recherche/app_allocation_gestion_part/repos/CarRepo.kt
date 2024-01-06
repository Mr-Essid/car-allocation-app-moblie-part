package org.recherche.app_allocation_gestion_part.repos

import android.os.Build
import androidx.annotation.RequiresApi
import org.recherche.app_allocation_gestion_part.models.CarRequest
import org.recherche.app_allocation_gestion_part.models.CarResponse
import org.recherche.app_allocation_gestion_part.models.CarUpdate
import org.recherche.app_allocation_gestion_part.models.Details
import org.recherche.app_allocation_gestion_part.network.netObject
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class CarRepo {
    val carAPI = netObject.getCarAPI()


    suspend fun getAllCars(token: String, id: Int? = null): Response<List<CarResponse>> {
        return carAPI.getAllCars(token, id)
    }

    suspend fun addCar(token: String, carRequest: CarRequest) : Response<CarResponse>{
        return carAPI.addCar(token, carRequest)
    }


    suspend fun updateCar(token: String, carUpdate: CarUpdate): Response<CarResponse> {
        return carAPI.updateCar(token, carUpdate)
    }


    suspend fun getAvailableCars(token: String): Response<List<CarResponse>> {
        return carAPI.getAvailableCars(token)
    }


    suspend fun activeCar(token: String, id: Int): Response<Details> {
        return carAPI.activeCar(token, id)
    }



    suspend fun disActiveCar(token: String, id: Int): Response<Details> {
        return carAPI.activeCar(token, id)
    }

    suspend fun getModels(token: String): Response<List<String>> {
        return carAPI.models(token)
    }

}