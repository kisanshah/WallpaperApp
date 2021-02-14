package com.example.wallpaperapp.model

data class WallpaperResponse(
    val data: List<WallPaper>,
    val meta: Meta
)

data class WallPaper(
    val id: String,
    val created_at: String,
    val path: String
)

data class Meta(
    val current_page: String,
    val last_page: String,
    val per_page: String
)