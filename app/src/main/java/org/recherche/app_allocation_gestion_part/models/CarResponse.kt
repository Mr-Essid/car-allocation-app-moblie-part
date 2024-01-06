package org.recherche.app_allocation_gestion_part.models

import org.recherche.app_allocation_gestion_part.databaseconfig.Car

data class CarResponse(
    val color_car: String,
    val id_car: Int,
    val identifyer_car: String,
    val is_active_car: Int,
    val is_allocated_car: Int,
    val is_mapped_car: Int,
    val model: String,
    val price_k_dinar: Double
) {
    constructor(car: Car): this(
        car.color_car,
        car.id_car,
        car.identifyer_car,
        car.is_active_car,
        car.is_allocated_car,
        car.is_mapped_car,
        car.model,
        car.price_k_dinar
    )
}