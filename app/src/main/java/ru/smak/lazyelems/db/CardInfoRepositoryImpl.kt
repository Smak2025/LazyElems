package ru.smak.lazyelems.db

import android.util.Log
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import ru.smak.lazyelems.db.internal.Card
import ru.smak.lazyelems.db.internal.CardColor
import ru.smak.lazyelems.db.internal.CardDao
import ru.smak.lazyelems.db.internal.CardInfo
import ru.smak.lazyelems.db.internal.ColorDao
import ru.smak.lazyelems.db.internal.ColoredCards

class CardInfoRepositoryImpl(cardDb: CardDatabase) : CardInfoRepository {

    private val cardsDao: CardDao = cardDb.cardsDao()
    private val colorDao: ColorDao = cardDb.colorsDao()

    override fun getAllCardsInfo(): Flow<List<CardInfo>> = cardsDao.getAllCardsInfo()

    override suspend fun getCardInfoById(cardId: Int): CardInfo? = cardsDao.getCardInfo(cardId)

    override suspend fun addCard(card: Card){
        cardsDao.insert(card)
    }

    override suspend fun editCard(card: Card) {
        cardsDao.update(card)
    }

    override suspend fun removeCard(card: Card) {
        cardsDao.delete(card)
    }

    override suspend fun addColor(color: Color) {
        colorDao.add(CardColor(color = color))
    }

    override suspend fun addColor(cardColor: CardColor) {
        colorDao.add(cardColor)
    }

    override suspend fun removeColor(color: Color) {
        colorDao.get(color)?.also{
            colorDao.remove(it)
        }
    }

    override suspend fun getColorById(colorId: Int): CardColor? = colorDao.get(colorId)

    override fun getAllColors(): Flow<List<CardColor>> = colorDao.getAll()

    override suspend fun getAllCardsByColor(color: Color): Flow<ColoredCards>? {
        return colorDao.get(color)?.id?.let{
            val res = colorDao.getAllCardsByColorId(it)
            res
        }
    }

}