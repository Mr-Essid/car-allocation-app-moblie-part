package org.recherche.app_allocation_gestion_part.databaseconfig

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDAO {

    @Query("SELECT * FROM usermodel")
    suspend fun getAll(): List<UserModel>

    @Query("SELECT * FROM usermodel WHERE id_user IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<UserModel>

    @Query("SELECT * FROM usermodel WHERE id_user = :id")
    suspend fun findByName(id: Int): UserModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: UserModel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserModel>)


    @Insert(onConflict = OnConflictStrategy.FAIL)
    suspend fun insert(users: UserModel)

    @Delete
    suspend fun delete(user: UserModel)

    @Query("DELETE FROM usermodel")
    suspend fun deleteFromDb()

    @Query("SELECT count(*) FROM usermodel")
    suspend fun getCountUser(): Int


}
