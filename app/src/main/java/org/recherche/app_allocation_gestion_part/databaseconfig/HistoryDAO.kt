package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface HistoryDAO {

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<History>


    @Insert
    suspend fun addHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun clearHistory()


    @Query("SELECT * FROM history WHERE id_history = :id")
    suspend fun getHistoryById(id: Int): List<History>



}