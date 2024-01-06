package org.recherche.app_allocation_gestion_part.models

import org.recherche.app_allocation_gestion_part.databaseconfig.History

data class HistoryResponse(
    val get_date: String,
    val id_car: Int,
    val id_history: Int,
    val id_user: Int,
    val is_active: Boolean,
    val is_dup: Boolean,
    val price_: Double,
    val ret_date: String
) {
    constructor(history: History): this(
        history.get_date,
        history.id_car,
        history.id_history,
        history.id_user,
        history.is_active,
        history.is_dup,
        history.price_,
        history.ret_date
    )
}