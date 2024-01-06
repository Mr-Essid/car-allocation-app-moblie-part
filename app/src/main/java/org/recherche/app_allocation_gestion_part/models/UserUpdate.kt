package org.recherche.app_allocation_gestion_part.models

data class UserUpdate(
    val id_user: Int,
    var is_banned: Boolean? = null,
    var name_user: String? = null,
    var points: Int? = null
)