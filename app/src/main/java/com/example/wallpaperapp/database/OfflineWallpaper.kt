package com.example.wallpaperapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpaper")
data class OfflineWallpaper(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "created_at")
    var created_at: String
)
