package com.example.wallpaperapp.repository

import androidx.lifecycle.LiveData
import com.example.wallpaperapp.database.OfflineWallpaper
import com.example.wallpaperapp.database.WallpaperDao
class RoomRepository(private val wallpaperDao: WallpaperDao) {

    fun getAllWallpaper(): LiveData<List<OfflineWallpaper>> = wallpaperDao.getWallpaper()

    suspend fun addWallpaper(offlineWallpaper: OfflineWallpaper) {
        wallpaperDao.addOfflineWallpaper(offlineWallpaper)
    }

}

