package org.recherche.app_allocation_gestion_part.models

data class UserRequest(
    val email_user: String,
    val ip_user: String,
    val name_user: String,
    val password_user: String
)