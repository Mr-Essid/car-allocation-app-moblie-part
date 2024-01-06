package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.room.Database
import androidx.room.RoomDatabase
import org.recherche.app_allocation_gestion_part.models.AdminResponse


@Database(entities = [UserModel::class, History::class, Car::class, AdminResponse::class], version = 1)
abstract class DatabaseConfig : RoomDatabase(){
    abstract fun carDAO(): CarDAO

    abstract fun userDAO(): UserDAO
    abstract fun historyDAO(): HistoryDAO
    abstract fun adminDAO(): AdminDAO

}
