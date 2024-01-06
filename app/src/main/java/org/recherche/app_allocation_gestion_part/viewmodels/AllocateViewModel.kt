package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.HistoryRequest
import org.recherche.app_allocation_gestion_part.repos.AllocateRepo


@RequiresApi(Build.VERSION_CODES.O)
class AllocateViewModel(application: Application): AndroidViewModel(application) {


    val isAllocated = MutableLiveData(false)
    var date = ""
    var price = ""
    var id_user = 0
    var id_car = 0
    var username = ""
    var caridentifier = ""
    val token_time = SessionManagement(application).getToken().split("_T_")
    val token = "Bearer ${token_time[0]}"
    val error = MutableLiveData("")

    val allocationRepo = AllocateRepo()
    fun submit(): Int {
        if (date.isEmpty())
            return -1
        if (price.isEmpty()) {
            return -2
        }

        if (id_car == 0) {
            return -3
        }
        if (id_user == 0) {
            return -4
        }

        val newHistory = HistoryRequest(id_car = id_car, id_user = id_user, ret_date = date, price_ = price.toDouble())
        viewModelScope.launch {
            val response = allocationRepo.allocate(token, newHistory)
            if (response.code() == 200 && response.body() != null) {
                isAllocated.postValue(true)
                Log.d("TAG", "submit: ${response.code()} ")

            }else {
                error.postValue("Error ${response.code()}")
                Log.d("TAG", "submit: ${response.code()} ")
            }
        }

        return 0

    }




}