package com.example.wallpaperapp.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpaper")
    fun getWallpaper(): LiveData<List<OfflineWallpaper>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOfflineWallpaper(offlineWallpaper: OfflineWallpaper)
}