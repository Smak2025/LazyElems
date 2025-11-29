package ru.smak.lazyelems.db

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun ColorToLong(value: Color?): Long? =
        value?.value?.toLong()

    @TypeConverter
    fun LongToColor(value: Long?): Color? = value?.let {
        Color.fromColorLong(value)
    }

}