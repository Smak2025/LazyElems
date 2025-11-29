package ru.smak.lazyelems.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorsDao {

    @Insert
    suspend fun add(color: Colors)

    @Query("SELECT * FROM colors")
    fun getAll(): Flow<List<Colors>>

    @Query("SELECT * FROM colors WHERE id = :id")
    fun get(id: Int): Flow<Colors>

}