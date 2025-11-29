package ru.smak.lazyelems.repos

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import ru.smak.lazyelems.db.Card
import ru.smak.lazyelems.db.CardColor
import ru.smak.lazyelems.db.CardInfo

interface CardInfoRepository {
    fun getAllCardsInfo(): Flow<List<CardInfo>>
    suspend fun getCardInfoById(cardId: Int): CardInfo?
    suspend fun addCard(card: Card)
    suspend fun editCard(card: Card)
    suspend fun removeCard(card: Card)

    suspend fun addColor(color: Color)
    suspend fun removeColor(color: Color)
    suspend fun getColorById(colorId: Int): CardColor?
    fun getAllColors(): Flow<List<CardColor>>
}