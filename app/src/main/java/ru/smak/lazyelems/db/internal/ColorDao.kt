package ru.smak.lazyelems.db.internal

import androidx.compose.ui.graphics.Color
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Insert
    suspend fun add(color: CardColor)

    @Delete
    suspend fun remove(color: CardColor)

    @Query("SELECT * FROM card_color")
    fun getAll(): Flow<List<CardColor>>

    @Query("SELECT * FROM card_color WHERE id = :id")
    suspend fun get(id: Int): CardColor?

    @Query("SELECT * FROM card_color WHERE color = :value")
    suspend fun get(value: Color): CardColor?

    @Transaction
    @Query("SELECT * FROM card_color JOIN card WHERE card_color.id = :colorId")
    fun getAllCardsByColorId(colorId: Int): Flow<ColoredCards>?

}