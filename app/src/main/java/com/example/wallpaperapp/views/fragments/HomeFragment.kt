package com.example.wallpaperapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.adapter.LoaderAdapter
import com.example.wallpaperapp.adapter.WallpaperAdapter
import com.example.wallpaperapp.database.OfflineWallpaper
import com.example.wallpaperapp.databinding.HomeFragmentBinding
import com.example.wallpaperapp.model.WallPaper
import com.example.wallpaperapp.viewmodel.MainViewModel

class HomeFragment : BaseFragment(), WallpaperAdapter.AdapterOnClickListener {
    private val viewModel by viewModels<MainViewModel>()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = HomeFragmentBinding.bind(view)

        val adapter = WallpaperAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter { adapter.retry() },
                footer = LoaderAdapter { adapter.retry() }
            )
        }
        val offlineWallpaper = OfflineWallpaper("uniquw", "qqq", "qqq")
        viewModel.addWallpaper(offlineWallpaper)
        viewModel.allWallpaper.observe(viewLifecycleOwner) {
            Log.d("MY_LOG", it.toString())
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
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchWallpaper(query, "", "")
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

    override fun onItemClick(wallPaper: WallPaper) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(wallPaper)
        findNavController().navigate(action)
    }

    override fun onFavClick(wallPaper: WallPaper) {
        val offlineWallpaper =
            OfflineWallpaper(wallPaper.id, wallPaper.path, wallPaper.created_at)
        viewModel.addWallpaper(offlineWallpaper)
        Toast.makeText(context, "Wallpaper added to favourite", Toast.LENGTH_SHORT).show()
    }

}