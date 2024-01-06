package org.recherche.app_allocation_gestion_part.models

import org.recherche.app_allocation_gestion_part.databaseconfig.UserModel

data class UserResponse(
    val created_at: String,
    val email_user: String,
    val id_user: Int,
    val ip_user: String?,
    val is_active: Boolean,
    val is_banned: Boolean,
    val name_user: String,
    val password_user: String,
    val points: Int,
    val updated_at: String
) {
    constructor(userModel: UserModel): this(
        userModel.created_at,
        userModel.email_user,
        userModel.id_user,
        userModel.ip_user,
        userModel.is_active,
        userModel.is_banned,
        userModel.name_user,
        userModel.password_user,
        userModel.points!!,
        userModel.updated_at!!
    )
}

