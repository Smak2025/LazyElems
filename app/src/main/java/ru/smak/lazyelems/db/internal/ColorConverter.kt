package ru.smak.lazyelems.db.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun colorToLong(value: Color?): Long? =
        value?.value?.toLong()

    @TypeConverter
    fun longToColor(value: Long?): Color? = value?.let {
        Color.fromColorLong(value)
    }

}