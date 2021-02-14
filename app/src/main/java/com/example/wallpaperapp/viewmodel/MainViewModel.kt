package com.example.wallpaperapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.wallpaperapp.model.WallPaper
import com.example.wallpaperapp.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mainRepository: MainRepository = MainRepository(application)


    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val wallPapers = mainRepository.getWallpaperByQuery("")

    fun searchWallpaper(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }

    fun getWallpapersByRatio(ratio: String): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaperByRatio(ratio)
    }

    fun getAllWallpapers(): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaper()
    }
}