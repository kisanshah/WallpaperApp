package com.example.wallpaperapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallpaperapp.model.WallPaper
import com.example.wallpaperapp.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var mainRepository: MainRepository = MainRepository(application)
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


    val wallPapers = currentQuery.switchMap {
        mainRepository.getWallpaperByQuery(it, RATIO).cachedIn(viewModelScope)
    }

    fun searchWallpaper(query: String, ratio: String) {
        currentQuery.value = query
        RATIO = ratio
    }

    companion object {
        private const val DEFAULT_QUERY = ""
        private var RATIO = ""
    }

    fun getWallpapersByRatio(ratio: String): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaperByRatio(ratio)
    }

    fun getAllWallpapers(): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaper()
    }
}