package org.recherche.app_allocation_gestion_part.databaseconfig

import android.content.Context
import androidx.room.Room

class getInstancreDatabase(val context: Context) {

        val db = Room.databaseBuilder(
            context,
            DatabaseConfig::class.java, "databaseNameAllocation"
        ).build()
}
