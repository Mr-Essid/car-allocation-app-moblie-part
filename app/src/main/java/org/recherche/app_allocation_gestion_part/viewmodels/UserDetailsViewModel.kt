package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.UserResponse
import org.recherche.app_allocation_gestion_part.models.UserUpdate
import org.recherche.app_allocation_gestion_part.repos.UserRepo
import kotlin.math.log


@RequiresApi(Build.VERSION_CODES.O)
class UserDetailsViewModel(application: Application)  : AndroidViewModel(application){

    val token_exptime = SessionManagement(application).getToken().split("_T_")
    val token = token_exptime[0]
    val exp_time = token_exptime[1]
    val currentUser = MutableLiveData<UserResponse>(null)
    var newUsername = mutableStateOf("")
    var newPoints = mutableStateOf("")
    val userRepo = UserRepo()
    val isUserUpdated = MutableLiveData(false)
    val isBanned = MutableLiveData(false)


    val messageError = MutableLiveData("")


    var current_username_to_update = "username"
    var current_points_to_update_str = "0"
    var current_points_to_update = 0


    private fun dataValidation(): Int {
        current_points_to_update = current_points_to_update_str.toInt()
        return if (currentUser.value!!.name_user == newUsername.value   &&
            currentUser.value!!.points.toString() == newPoints.value )
            -1
        else 0
    }

    fun updateUser() : Int{



        val res = dataValidation()
        if (res < 0) {
            return res
        }
        if (currentUser.value == null) {
            return -2
        }

        val newUserInfo = UserUpdate(id_user = currentUser.value!!.id_user)

        if (currentUser.value!!.name_user != newUsername.value ) {
            newUserInfo.name_user = newUsername.value
        }


        if (currentUser.value!!.points.toString() != newPoints.value ) {
            newUserInfo.points = newPoints.value!!.toInt()
        }



        viewModelScope.launch {
            val response = userRepo.updateUser(token, newUserInfo)

            if (response.code() == 200 && response.body() != null) {
                isUserUpdated.postValue(true)
            }
            else
                messageError.postValue("Error ${response.code()}")
        }
        return 0
    }


    fun banneUser() {
        viewModelScope.launch {
            val response = userRepo.banneUser(token, currentUser.value!!.id_user)

            if (response.code() == 200 && response.body() != null) {
                Log.d("TAG", "banneUser: banned user")
            }
        }
    }


    fun deBanneUser() {
        viewModelScope.launch {
            val response = userRepo.debanneUser(token, currentUser.value!!.id_user)

            if (response.code() == 200 && response.body() != null) {
                Log.d("TAG", "deBanneUser: is debanned")
            }
        }
    }

    fun getUser(id: Int) {

        viewModelScope.launch {
            val response = userRepo.getUserById(id, token)

            if (response.code() == 200 && response.body() != null) {
                currentUser.postValue(response.body())
                newUsername.value = response.body()!!.name_user
                newPoints.value = response.body()!!.points.toString()

                Log.d("TAG", "getUser: The User Come")
            }else
                messageError.postValue("There is problem ${response.code()}")

        }
    }

}