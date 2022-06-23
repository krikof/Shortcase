package com.example.shortcaserc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ComicObject::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ComicDatabase : RoomDatabase() {

    abstract fun comicDAO(): ComicDAO

    companion object {
        @Volatile
        private var INSTANCE: ComicDatabase? = null

        fun getDatabase(context: Context): ComicDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ComicDatabase::class.java, "comic_database"
            ).build()
    }
}