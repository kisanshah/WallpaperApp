package com.example.wallpaperapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.databinding.WallpaperBinding
import com.example.wallpaperapp.model.WallPaper

class WallpaperAdapter(val listener: AdapterOnClickListener) :
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

    inner class WallpaperHolder(private val binding: WallpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }

            binding.fav.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onFavClick(item)
                    }
                }
            }

        }

        fun bind(wallPaper: WallPaper) {
            binding.apply {
                Glide.with(itemView)
                    .load(wallPaper.path)
                    .centerCrop()
                    .into(imageView)
//                container.setBackgroundColor(Color.parseColor(wallPaper.colors[1]))
            }
        }
    }

    interface AdapterOnClickListener {
        fun onItemClick(wallPaper: WallPaper)
        fun onFavClick(wallPaper: WallPaper)
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