package com.example.wallpaperapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.wallpaperapp.database.OfflineWallpaper
import com.example.wallpaperapp.database.RoomDB
import com.example.wallpaperapp.model.WallPaper
import com.example.wallpaperapp.repository.MainRepository
import com.example.wallpaperapp.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mainRepository: MainRepository
    private var roomRepository: RoomRepository

    val allWallpaper: LiveData<List<OfflineWallpaper>>


    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val currentRatio = MutableLiveData(RATIO)
    private val currentSorting = MutableLiveData(SORTING)

    init {
        val wallpaperDao = RoomDB.getInstance(application.applicationContext).WallpaperDao()
        mainRepository = MainRepository()
        roomRepository = RoomRepository(wallpaperDao)
        allWallpaper = roomRepository.getAllWallpaper()
    }

    fun addWallpaper(offlineWallpaper: OfflineWallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addWallpaper(offlineWallpaper)
        }
    }

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