package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Insert
    suspend fun add(color: CardColor)

    @Delete
    suspend fun remove(color: CardColor)

    @Query("SELECT * FROM colors")
    fun getAll(): Flow<List<CardColor>>

    @Query("SELECT * FROM colors WHERE id = :id")
    suspend fun get(id: Int): CardColor?

    @Query("SELECT * FROM colors WHERE value = :value")
    suspend fun get(value: Color): CardColor?

}