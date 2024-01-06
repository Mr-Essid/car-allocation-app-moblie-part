package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.network.AdminAPI
import org.recherche.app_allocation_gestion_part.network.netObject
import org.recherche.app_allocation_gestion_part.repos.AdminRepo

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "LoginViewModel"
    val sessionManagement = SessionManagement(app)
    var error = MutableLiveData("")

    var isGotTheToken = MutableLiveData(false)

    val adminAPI = AdminRepo()

    fun dataValidation(username: String, password: String): Int {

        if (username.length < 4 || !username.contains("@"))
            return -1

        if (password.length < 6) {
            return -2
        }

        return 0

    }

    fun login(username: String, password: String) : Int{

        val res = dataValidation(username, password)
        if (res < 0)
            return res
        viewModelScope.launch {
            val response = adminAPI.loginAdmin(username, password)
            if (response.code() == 401) {
                error.postValue("INVALID USERNAME OR EMAIL")
            }else if (response.code() != 200)
                error.postValue("ERROR ${response.code()}")
            else
            response.let {
                val tokenResponse = it.body()
                val token_ = tokenResponse!!.access_token + "_T_" + tokenResponse.exp_time
                isGotTheToken.postValue(true)
                sessionManagement.addToken(token_)
            }
        }

        return 0
    }







}