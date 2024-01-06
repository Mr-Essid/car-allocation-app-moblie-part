package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.recherche.app_allocation_gestion_part.models.UserResponse


@Entity
data class UserModel(
    val created_at: String,
    val email_user: String,
    @PrimaryKey
    val id_user: Int,
    val ip_user: String?,
    val is_active: Boolean,
    val is_banned: Boolean,
    val name_user: String,
    val password_user: String,
    val points: Int?,
    val updated_at: String?,
) {
    constructor(userResponse: UserResponse): this(
        userResponse.created_at,
        userResponse.email_user,
        userResponse.id_user,
        userResponse.ip_user,
        userResponse.is_active,
        userResponse.is_banned,
        userResponse.name_user,
        userResponse.password_user,
        userResponse.points,
        userResponse.updated_at,

    )
}