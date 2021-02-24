package com.example.wallpaperapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [OfflineWallpaper::class], exportSchema = false, version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun WallpaperDao(): WallpaperDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null
        fun getInstance(context: Context): RoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RoomDB::class.java,
                    "wallpaper"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}