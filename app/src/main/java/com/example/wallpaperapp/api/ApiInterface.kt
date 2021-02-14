package com.example.wallpaperapp.api


import com.example.wallpaperapp.model.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {


    @GET("search")
    suspend fun getWallpaperByRatio(@Query("ratios") ratio: String): Response<WallpaperResponse>

    @GET("search")
    suspend fun getAllWallpaper(@Query("page") page: Int): WallpaperResponse

    @GET("search")
    suspend fun getWallpaperByQuery(
        @Query("page") page: Int,
        @Query("q") query: String
    ): WallpaperResponse
}