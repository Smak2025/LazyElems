package ru.smak.lazyelems.repos

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import ru.smak.lazyelems.db.Card
import ru.smak.lazyelems.db.CardColor
import ru.smak.lazyelems.db.CardDao
import ru.smak.lazyelems.db.CardInfo
import ru.smak.lazyelems.db.ColorDao

class CardInfoRepositoryImpl(val cardDao: CardDao, val colorDao: ColorDao) : CardInfoRepository {

    override fun getAllCardsInfo(): Flow<List<CardInfo>> = cardDao.getAllCardsInfo()

    override suspend fun getCardInfoById(cardId: Int): CardInfo? = cardDao.getCardInfo(cardId)

    override suspend fun addCard(card: Card){
        cardDao.insert(card)
    }

    override suspend fun editCard(card: Card) {
        cardDao.update(card)
    }

    override suspend fun removeCard(card: Card) {
        cardDao.delete(card)
    }

    override suspend fun addColor(color: Color) {
        colorDao.add(CardColor(value = color))
    }

    override suspend fun removeColor(color: Color) {
        colorDao.get(color)?.also{
            colorDao.remove(it)
        }
    }

    override suspend fun getColorById(colorId: Int): CardColor? = colorDao.get(colorId)

    override fun getAllColors(): Flow<List<CardColor>> = colorDao.getAll()

}