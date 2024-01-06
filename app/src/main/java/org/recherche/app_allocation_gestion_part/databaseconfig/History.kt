package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.recherche.app_allocation_gestion_part.models.HistoryResponse


@Entity
data class History(
    val get_date: String,
    val id_car: Int,
    @PrimaryKey
    val id_history: Int,
    val id_user: Int,
    val is_active: Boolean,
    val is_dup: Boolean,
    val price_: Double,
    val ret_date: String
) {

    constructor(historyResponse: HistoryResponse): this(
        historyResponse.get_date,
        historyResponse.id_car,
        historyResponse.id_history,
        historyResponse.id_user,
        historyResponse.is_active,
        historyResponse.is_dup,
        historyResponse.price_,
        historyResponse.ret_date
    )

}
