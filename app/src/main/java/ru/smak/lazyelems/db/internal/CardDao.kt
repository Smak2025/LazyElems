package ru.smak.lazyelems.db.internal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(entity = Card::class)
    suspend fun insert(card: Card)

    @Delete(entity = Card::class)
    suspend fun delete(card: Card)

    @Query("SELECT * FROM card ORDER BY title, text")
    fun getAllCards(): Flow<List<Card>>

    @Query("SELECT * FROM card WHERE id=:id")
    suspend fun getCardById(id: Int): Card?

    @Transaction
    @Query("SELECT * FROM card INNER JOIN colors ON card.colorId == colors.id ORDER BY modification_date DESC, title, text")
    fun getAllCardsInfo(): Flow<List<CardInfo>>

    @Transaction
    @Query("SELECT * FROM card INNER JOIN colors ON card.colorId == colors.id WHERE card.id=:id")
    suspend fun getCardInfo(id: Int): CardInfo?

    @Update
    suspend fun update(card: Card)
}