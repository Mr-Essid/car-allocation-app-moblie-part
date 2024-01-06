package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CarDAO {
    @Query("SELECT * FROM car")
    suspend fun getAllCars(): List<Car>

    @Query("SELECT * FROM car WHERE id_car = :id")
    suspend fun getCarById(id: Int): Car


    @Insert
    suspend fun addCar(car: Car)

    @Update
    suspend fun upDateCar(car: Car)

    @Delete
    suspend fun delete(user: Car)

    @Query("DELETE FROM car")
    suspend fun deleteFromCars()


    @Query("SELECT count(*) FROM car")
    suspend fun countCars(): Int


}