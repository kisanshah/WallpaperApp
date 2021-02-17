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

    var mainRepository: MainRepository = MainRepository()
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val currentRatio = MutableLiveData(RATIO)
    private val currentSorting = MutableLiveData(SORTING)


    val wallPapers = currentQuery.switchMap {
        mainRepository.getWallpaperByQuery(it, currentRatio.value!!, currentSorting.value!!)
            .cachedIn(viewModelScope)
    }

    fun searchWallpaper(query: String, ratio: String, sorting: String) {
        currentQuery.value = query
        currentRatio.value = ratio
        currentSorting.value = sorting
    }

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val RATIO = ""
        private const val SORTING = "views"
    }

    fun getWallpapersByRatio(ratio: String): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaperByRatio(ratio)
    }

    fun getAllWallpapers(): MutableLiveData<List<WallPaper>> {
        return mainRepository.getWallpaper()
    }
}