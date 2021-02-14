package com.example.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallpaperapp.databinding.WallpaperBinding
import com.example.wallpaperapp.model.WallPaper

class WallpaperAdapter() :
    PagingDataAdapter<WallPaper, WallpaperAdapter.WallpaperHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder {
        val binding = WallpaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WallpaperHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class WallpaperHolder(private var binding: WallpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wallPaper: WallPaper) {
            binding.apply {
                Glide.with(itemView)
                    .load(wallPaper.path)
                    .centerCrop()
                    .into(imageView)
            }
        }
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