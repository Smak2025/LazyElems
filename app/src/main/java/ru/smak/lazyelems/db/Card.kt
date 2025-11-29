package ru.smak.lazyelems.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "card",
    indices = [

    ],
    foreignKeys = [
        ForeignKey(
            CardColor::class,
            ["id"],
            ["colorId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = CASCADE,
        )
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    var text: String,
    var colorId: Int = 0,
    @ColumnInfo(name = "modification_date")
    var modified: LocalDateTime = LocalDateTime.now()
)