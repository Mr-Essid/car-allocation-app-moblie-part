package org.recherche.app_allocation_gestion_part.models

data class TokenResponse(
    val access_token: String,
    val exp_time: String,
    val token_type: String
)