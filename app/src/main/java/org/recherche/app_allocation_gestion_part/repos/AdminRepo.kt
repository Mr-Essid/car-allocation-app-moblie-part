package org.recherche.app_allocation_gestion_part.repos

import android.os.Build
import androidx.annotation.RequiresApi
import org.recherche.app_allocation_gestion_part.models.AdminResponse
import org.recherche.app_allocation_gestion_part.models.CountResResponse
import org.recherche.app_allocation_gestion_part.models.TokenResponse
import org.recherche.app_allocation_gestion_part.models.UserResponse
import org.recherche.app_allocation_gestion_part.network.AdminAPI
import org.recherche.app_allocation_gestion_part.network.netObject
import retrofit2.Response

class AdminRepo {

    @RequiresApi(Build.VERSION_CODES.O)
    private val adminAPI = netObject.getAdminAPI()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loginAdmin(username: String, password: String): Response<TokenResponse> {
        return adminAPI.loginAdmin(username, password)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCurrentUser(token: String): Response<AdminResponse> {
        return adminAPI.getCurrentUser("Bearer $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCountAll(token: String): Response<CountResResponse> {
        return adminAPI.getCountAll("Bearer $token")
    }

}