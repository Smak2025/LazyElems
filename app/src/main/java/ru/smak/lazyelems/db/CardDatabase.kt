package ru.smak.lazyelems.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.smak.lazyelems.db.internal.Card
import ru.smak.lazyelems.db.internal.CardColor
import ru.smak.lazyelems.db.internal.CardDao
import ru.smak.lazyelems.db.internal.ColorConverter
import ru.smak.lazyelems.db.internal.ColorDao
import ru.smak.lazyelems.db.internal.DateTimeConverter
import ru.smak.lazyelems.db.internal.migrations.MIGRATION_1_2

@Database(entities = [Card::class, CardColor::class], version = 2, exportSchema = false)
@TypeConverters(DateTimeConverter::class, ColorConverter::class)
abstract class CardDatabase: RoomDatabase(){
    abstract fun cardsDao(): CardDao
    abstract fun colorsDao(): ColorDao

    companion object {
        @Volatile
        private lateinit var instance: CardDatabase

        fun getInstance(context: Context): CardDatabase {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context,
                    CardDatabase::class.java,
                    "db_cards"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return instance
        }
    }
}