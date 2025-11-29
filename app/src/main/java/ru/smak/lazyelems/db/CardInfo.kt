package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import java.time.LocalDateTime

data class CardInfo(
    @Embedded var card: Card,
    @Relation(
        parentColumn = "colorId",
        entityColumn = "id"
    )
    var color: CardColor?
)
