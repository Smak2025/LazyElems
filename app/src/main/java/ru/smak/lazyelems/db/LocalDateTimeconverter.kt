package ru.smak.lazyelems.db

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeconverter {

    @TypeConverter
    fun longToLocalDateTime(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofEpochSecond(
            value,
            0,
            ZoneOffset.of("+00")
        )
    }

    @TypeConverter
    fun localDateTimeToLong(value: LocalDateTime?): Long? =
        value?.toEpochSecond(ZoneOffset.of("+00"))

}