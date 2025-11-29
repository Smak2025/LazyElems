package ru.smak.lazyelems.db.internal

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

data class ColoredCards(
    @Embedded
    var cardColor: CardColor,
    @Relation(
        parentColumn = "id",
        entityColumn = "colorId"
    )
    val cards: List<Card>

)
