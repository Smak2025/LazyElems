package ru.smak.lazyelems.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Card::class, CardColor::class], version = 2, exportSchema = true)
@TypeConverters(DateTimeConverter::class, ColorConverter::class)
abstract class CardDatabase : RoomDatabase(){
    abstract fun cardsDao(): CardDao
    abstract fun colorsDao(): ColorDao

    companion object {
        private var instance: CardDatabase? = null

        val cardsDao: CardDao? get() = instance?.cardsDao()
        val colorDao: ColorDao? get() = instance?.colorsDao()

        fun initDb(context: Context) = instance ?: Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            "db_cards"
        ).build().also { instance = it }
    }
}