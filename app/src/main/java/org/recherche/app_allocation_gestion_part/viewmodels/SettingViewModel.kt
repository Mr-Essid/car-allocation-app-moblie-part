package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.recherche.app_allocation_gestion_part.SessionManagement
import org.recherche.app_allocation_gestion_part.models.AdminResponse

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    val currentAdmin = MutableLiveData<AdminResponse>(null)
    val isLogOut = MutableLiveData(false)
    val error = MutableLiveData("")
    val isSubmit = MutableLiveData(false)
    val sessionManagement = SessionManagement(application)


    fun updateAdmin() {


    }


    fun logOut() {
        sessionManagement.removeAuthority()
        sessionManagement.removeToken()
        isLogOut.postValue(true)
    }




}