package com.example.wallpaperapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.wallpaperapp.api.ApiBuilder
import com.example.wallpaperapp.api.ApiInterface
import com.example.wallpaperapp.api.WallpaperPagingSource
import com.example.wallpaperapp.model.WallPaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository() {
    private val TAG = "DEBUG"
    var apiInterface: ApiInterface = ApiBuilder.buildService(ApiInterface::class.java)
    var liveDataWallpaper: MutableLiveData<List<WallPaper>> = MutableLiveData()

    fun getWallpaperByRatio(ratio: String): MutableLiveData<List<WallPaper>> {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiInterface.getWallpaperByRatio(ratio)
            if (response.isSuccessful) {
                liveDataWallpaper.postValue(response.body()?.data)
            } else {
                Log.d(TAG, "getWallpaper: " + response.errorBody())
            }
        }
        return liveDataWallpaper
    }

    fun getWallpaperByQuery(
        query: String,
        ratio: String,
        sorting: String
    ): LiveData<PagingData<WallPaper>> {
        return Pager(
            config = PagingConfig(
                pageSize = 24,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { WallpaperPagingSource(apiInterface, query, ratio,sorting) }
        ).liveData
    }

    fun getWallpaper(): MutableLiveData<List<WallPaper>> {

//        CoroutineScope(Dispatchers.IO).launch {
//            val response = apiInterface.getAllWallpaper()
//            if (response.isSuccessful) {
//                liveDataWallpaper.postValue(response.body()?.data)
//                Log.d(TAG, "getWallpaper: " + response.body()?.meta)
//            } else {
//                Log.d(TAG, "getWallpaper: " + response.errorBody())
//            }
//        }
        return liveDataWallpaper
    }
}