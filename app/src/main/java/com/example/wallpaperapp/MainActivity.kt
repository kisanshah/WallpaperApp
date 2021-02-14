package com.example.wallpaperapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperapp.adapter.WallpaperAdapter
import com.example.wallpaperapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = "DEBUG"

    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = WallpaperAdapter(this)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)

        viewModel.wallPapers.observe(this, Observer {
            adapter.submitData(this.lifecycle, it)
        })


    }
}