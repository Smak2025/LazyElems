package ru.smak.lazyelems.db.internal

import androidx.room.Embedded
import androidx.room.Relation

data class CardInfo(
    @Embedded
    var card: Card,
    @Relation(
        parentColumn = "colorId",
        entityColumn = "id"
    )
    var cardColor: CardColor?
)