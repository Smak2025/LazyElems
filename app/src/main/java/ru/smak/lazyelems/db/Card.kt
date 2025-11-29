package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "card",
    foreignKeys = [
        ForeignKey(
            Colors::class,
            ["id"],
            ["color"],
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
    var color: Int = 0,
    @ColumnInfo(name = "modification_date")
    var modified: LocalDateTime = LocalDateTime.now()
)