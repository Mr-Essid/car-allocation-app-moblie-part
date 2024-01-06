package org.recherche.app_allocation_gestion_part.models

data class AdminRequest(
    val email_admin: String,
    val ip_admin: String,
    val name_admin: String,
    val password_admin: String
)