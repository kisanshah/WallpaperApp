package com.example.wallpaperapp.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.model.WallPaper
import retrofit2.HttpException
import java.io.IOException

private const val START_INDEX = 1

class WallpaperPagingSource(
    private val apiInterface: ApiInterface,
    private val query: String,
    private val ratio: String,
    private val sorting: String
) :
    PagingSource<Int, WallPaper>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallPaper> {
        val position = params.key ?: START_INDEX

        return try {
            val response = apiInterface.getWallpaperByQuery(position, query, ratio,sorting)
            val wallPaper = response.data
            LoadResult.Page(
                data = wallPaper,
                prevKey = if (position == START_INDEX) null else position - 1,
                nextKey = if (wallPaper.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, WallPaper>): Int? {
        TODO("Not yet implemented")
    }
}