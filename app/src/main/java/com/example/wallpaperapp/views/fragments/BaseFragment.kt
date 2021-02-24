package com.example.wallpaperapp.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.wallpaperapp.views.MainActivity

abstract class BaseFragment : Fragment() {

    protected open var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var mainActivity: MainActivity
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity) {
            mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }


}