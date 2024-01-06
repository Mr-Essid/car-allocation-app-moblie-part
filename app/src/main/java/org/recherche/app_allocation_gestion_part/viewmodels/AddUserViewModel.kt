package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.UserRequest
import org.recherche.app_allocation_gestion_part.repos.UserRepo

class AddUserViewModel(application: Application): AndroidViewModel(application) {

    private val sessionManagement = SessionManagement(application)
    private val token_exptime = sessionManagement.getToken().split("_T_")
    private val token = token_exptime[0]
    private val exp_time = token_exptime[1]
    val error = MutableLiveData("")
    val userAdded = MutableLiveData(false)
    @RequiresApi(Build.VERSION_CODES.O)
    val userRepo = UserRepo()

    var userName = ""
    var password = ""
    var email = ""
    var ipAddress = "localhost"
    var passwordConfirmation = ""


    fun dataValidation(): Int {
        if (userName.length < 4) {
            return -1
        }else if (email.length < 4 || ! email.contains("@")) {
            return -2
        }else if (password.length < 6) {
            return -3
        }else if (password != passwordConfirmation) {
            return -4
        }
            return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addUser(): Int {
        val res = dataValidation()
        if (res < 0) {
            return res
        }
        val newUser = UserRequest(name_user = userName, email_user = email, ip_user = ipAddress, password_user = password)

        viewModelScope.launch {
            val response = userRepo.addUser(newUser, token)
            if (response.body() != null && response.code() == 200) {
                userAdded.postValue(true)
            }else {
                error.postValue("Error ${response.code()}")
            }
        }

        return res
    }








}