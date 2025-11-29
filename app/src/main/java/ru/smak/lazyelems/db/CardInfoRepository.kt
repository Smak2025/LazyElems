package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.smak.lazyelems.db.internal.Card
import ru.smak.lazyelems.db.internal.CardColor
import ru.smak.lazyelems.db.internal.CardInfo
import ru.smak.lazyelems.db.internal.ColoredCards

interface CardInfoRepository {
    fun getAllCardsInfo(): Flow<List<CardInfo>>
    suspend fun getCardInfoById(cardId: Int): CardInfo?
    suspend fun addCard(card: Card)
    suspend fun editCard(card: Card)
    suspend fun removeCard(card: Card)

    suspend fun addColor(color: Color)
    suspend fun addColor(cardColor: CardColor)
    suspend fun removeColor(color: Color)
    suspend fun getColorById(colorId: Int): CardColor?
    fun getAllColors(): Flow<List<CardColor>>

    suspend fun getAllCardsByColor(color: Color): Flow<ColoredCards>?
}