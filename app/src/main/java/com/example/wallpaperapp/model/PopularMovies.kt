package com.example.wallpaperapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallpaperResponse(
    val data: List<WallPaper>,
    val meta: Meta
) : Parcelable

@Parcelize
data class WallPaper(
    val id: String,
    val created_at: String,
    val resolution: String,
    val ratio: String,
    val path: String,
    val colors: List<String>
) : Parcelable


@Parcelize
data class Meta(
    val current_page: String,
    val last_page: String,
    val per_page: String
) : Parcelable