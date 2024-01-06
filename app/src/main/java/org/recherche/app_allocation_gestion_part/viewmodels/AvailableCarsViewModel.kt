package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.CarResponse
import org.recherche.app_allocation_gestion_part.repos.CarRepo


@RequiresApi(Build.VERSION_CODES.O)
class AvailableCarsViewModel(application: Application) : AndroidViewModel(application) {
    val listCars = MutableLiveData<List<CarResponse>>(null)
    val error = MutableLiveData("")
    val carRepo = CarRepo()
    val token_extime = SessionManagement(application).getToken().split("_T_")
    val token = "Bearer ${token_extime[0]}"


    fun getAllAvailableCars() {

        viewModelScope.launch {
            val responce = carRepo.getAvailableCars(token)
            if (responce.code() == 200 && responce.body() != null) {
                listCars.postValue(responce.body())
            }else {
                error.postValue("Error ${responce.code()}")
            }

        }
    }








}