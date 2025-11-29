package ru.smak.lazyelems.db.internal.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Создаём таблицу colors
        db.execSQL("""
        CREATE TABLE IF NOT EXISTS `card_color` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `color` INTEGER NOT NULL
        )
    """.trimIndent())

        // 2. Создаём новую таблицу card с нужной структурой (включая colorId и foreign key)
        db.execSQL("""
        CREATE TABLE `card_new` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `title` TEXT NOT NULL,
            `text` TEXT NOT NULL,
            `color_id` INTEGER NOT NULL DEFAULT 1,
            `modification_date` INTEGER NOT NULL DEFAULT (strftime('%s', 'now')),
            FOREIGN KEY(`color_id`) REFERENCES `card_color`(`id`)
                ON UPDATE CASCADE
                ON DELETE RESTRICT
        )
    """.trimIndent())

        // 3. Переносим данные из старой таблицы card (без colorId и modification_date)
        db.execSQL("""
        INSERT INTO `card_new` (`id`, `title`, `text`, `color_id`, `modification_date`)
        SELECT 
            `id`, 
            `title`, 
            `text`, 
            1, 
            strftime('%s', 'now')
        FROM `card`
    """.trimIndent())

        // 4. Удаляем старую таблицу
        db.execSQL("DROP TABLE `card`")

        // 5. Переименовываем новую таблицу
        db.execSQL("ALTER TABLE `card_new` RENAME TO `card`")

        // 6. Создаём индекс (ROOM сам его создаст, но можно явно)
        db.execSQL("CREATE INDEX IF NOT EXISTS `color_idx` ON `card` (`color_id`)")
    }
}
