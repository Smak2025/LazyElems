package ru.smak.lazyelems.db.internal

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeConverter {

    @TypeConverter
    fun localDateTimeToLong(value: LocalDateTime?): Long? =
        value?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun longToLocalDateTime(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofEpochSecond(
            value,
            0,
            ZoneOffset.UTC
        )
    }
}