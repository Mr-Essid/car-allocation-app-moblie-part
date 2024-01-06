package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.compose.ui.text.style.LineBreak
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.recherche.app_allocation_gestion_part.models.AdminResponse


@Dao
interface AdminDAO {


    @Query("SELECT * FROM adminresponse WHERE id_admin = :id")
    suspend fun getAdminById(id: Int): AdminResponse


    @Query("SELECT * FROM adminresponse")
    suspend fun getAllAdmins(): List<AdminResponse>

    @Insert
    suspend fun insertAdmin(adminResponse: AdminResponse)


    @Delete
    suspend fun deleteAdmin(adminResponse: AdminResponse)

    @Query("DELETE FROM adminresponse")
    suspend fun clearAllAdminInfo()


    @Update
    suspend fun updateAdmin(adminResponse: AdminResponse)

    @Query("SELECT count(*) FROM adminresponse")
    suspend fun getAdminCount(): Int


}