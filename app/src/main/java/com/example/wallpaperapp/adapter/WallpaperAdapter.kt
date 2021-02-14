package com.example.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.model.WallPaper

class WallpaperAdapter(private val context: Context) :
    PagingDataAdapter<WallPaper, WallpaperAdapter.WallpaperHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper, parent, false)
        return WallpaperHolder(view)
    }

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        val currentItem = getItem(position)
        Glide.with(context).load(currentItem?.path).centerCrop().into(holder.wallpaper)

    }

    class WallpaperHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wallpaper: ImageView = itemView.findViewById(R.id.wallpaper)
    }


    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<WallPaper>() {
            override fun areItemsTheSame(oldItem: WallPaper, newItem: WallPaper) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WallPaper, newItem: WallPaper) =
                oldItem == newItem

        }
    }
}