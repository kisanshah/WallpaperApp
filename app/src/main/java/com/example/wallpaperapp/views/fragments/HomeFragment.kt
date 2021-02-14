package com.example.wallpaperapp.views.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.adapter.LoaderAdapter
import com.example.wallpaperapp.adapter.WallpaperAdapter
import com.example.wallpaperapp.databinding.HomeFragmentBinding
import com.example.wallpaperapp.viewmodel.MainViewModel

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<MainViewModel>()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = HomeFragmentBinding.bind(view)

        val adapter = WallpaperAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter { adapter.retry() },
                footer = LoaderAdapter { adapter.retry() }
            )
        }

        viewModel.wallPapers.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchWallpaper(query,"16x9")
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}