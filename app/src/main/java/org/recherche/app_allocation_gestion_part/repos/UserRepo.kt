package org.recherche.app_allocation_gestion_part.repos

import android.os.Build
import androidx.annotation.RequiresApi
import org.recherche.app_allocation_gestion_part.models.UserRequest
import org.recherche.app_allocation_gestion_part.models.UserResponse
import org.recherche.app_allocation_gestion_part.models.UserUpdate
import org.recherche.app_allocation_gestion_part.network.UserAPI
import org.recherche.app_allocation_gestion_part.network.netObject
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class UserRepo {
    val userAPI = netObject.getUserAPI()


    suspend fun getAllUsers(token: String) : Response<List<UserResponse>>{
        return userAPI.getAllUsers("Bearer $token")
    }


    suspend fun getUserById(id: Int, token: String) : Response<UserResponse>{
        return userAPI.getUserById(id, "Bearer $token")
    }


    suspend fun getBannedUsers(token: String) : Response<List<UserResponse>>{
        return userAPI.getAllBannedUsers("Bearer $token")
    }


    suspend fun getActiveUsers(token: String) : Response<List<UserResponse>>{
        return userAPI.getAllActiveUsers("Bearer $token")
    }

    suspend fun addUser(userRequest: UserRequest, token: String): Response<UserResponse> {
        return userAPI.addUser(userRequest, "Bearer $token")
    }


    suspend fun updateUser(token: String, userUpdate: UserUpdate): Response<UserResponse> {
        return userAPI.update(userUpdate, "Bearer $token")
    }


    suspend fun getAllBannedUsers(token: String) : Response<List<UserResponse>>{
        return userAPI.getAllBannedUsers("Bearer $token")
    }


    suspend fun banneUser(token: String, id: Int): Response<UserResponse> {
        return userAPI.banneUser(id, "Bearer $token")
    }


    suspend fun debanneUser(token: String, id: Int): Response<UserResponse> {
        return userAPI.debanneUser(id, "Bearer $token")
    }





}