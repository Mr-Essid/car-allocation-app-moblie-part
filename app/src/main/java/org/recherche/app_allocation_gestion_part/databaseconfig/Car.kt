package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.recherche.app_allocation_gestion_part.models.CarResponse


@Entity
data class Car(
    var color_car: String,
    @PrimaryKey
    val id_car: Int,
    val identifyer_car: String,
    val is_active_car: Int,
    val is_allocated_car: Int,
    val is_mapped_car: Int,
    val model: String,
    val price_k_dinar: Double

) {
    constructor(carResponse: CarResponse):
            this(
                carResponse.color_car,
                carResponse.id_car,
                carResponse.identifyer_car,
                carResponse.is_active_car,
                carResponse.is_allocated_car,
                carResponse.is_mapped_car,
                carResponse.model,
                carResponse.price_k_dinar
            )
}
