package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class Colors(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val value: Color = Color.Unspecified,
)