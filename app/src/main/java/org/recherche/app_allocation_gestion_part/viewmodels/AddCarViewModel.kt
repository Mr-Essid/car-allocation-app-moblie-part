package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.CarRequest
import org.recherche.app_allocation_gestion_part.repos.CarRepo


@RequiresApi(Build.VERSION_CODES.O)

class AddCarViewModel(application: Application) : AndroidViewModel(application) {

    val caridentifier = mutableStateOf("")
    val carModel = mutableStateOf("")
    val carColor = mutableStateOf("")
    val carPrice = mutableStateOf("")
    val error = MutableLiveData("")
    val isSubmit = MutableLiveData(false)

    val token_exptime = SessionManagement(application).getToken().split("_T_")
    val token = "Bearer ${token_exptime[0]}"


    val carRepo = CarRepo()






    fun addCar(): Int {
        if (carModel.value.isEmpty() || carModel.value.isEmpty() || carColor.value.isEmpty() || carPrice.value.toDouble() == 0.0) {
            return -1
        }


        val newCar = CarRequest(
            carColor.value,
            caridentifier.value,
            carModel.value,
            carPrice.value.toDouble()
            )


        viewModelScope.launch {

            val response = carRepo.addCar(token, newCar)

            if (response.code() == 200 && response.body() != null) {
                isSubmit.postValue(true)
            }
            else
                error.postValue("Error ${response.code()}")

        }




        return 0
    }



}