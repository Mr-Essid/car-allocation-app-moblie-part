package org.recherche.app_allocation_gestion_part.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.recherche.app_allocation_gestion_part.databaseconfig.UserModel


@Entity
data class AdminResponse(
    val authority: Int,
    val created_at: String,
    val email_admin: String,
    @PrimaryKey
    val id_admin: Int,
    val ip_admin: String,
    val is_active: Boolean,
    val name_admin: String,
    val password_admin: String,
    val updated_at: String
)
