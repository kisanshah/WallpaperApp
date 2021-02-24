package com.example.wallpaperapp.views.fragments

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.DetailFragmentBinding
import com.example.wallpaperapp.databinding.DialogBinding
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class DetailFragment : BaseFragment() {
    private val TAG = "DEBUG"
    private lateinit var selectedType: String
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var binding: DetailFragmentBinding
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var bitmap: Bitmap
    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailFragmentBinding.bind(view)

        selectedType = "Cropped"

        Glide.with(this@DetailFragment)
            .asBitmap()
            .load(args.wallpaper.path)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.d(TAG, "onLoadCleared: Clear")
                }
            })

        binding.apply {
            val wallPaper = args.wallpaper
            Glide.with(this@DetailFragment)
                .asBitmap()
                .load(wallPaper.path)
                .centerCrop()
                .into(wallpaper)

            set.setOnClickListener {
                showDialog()
            }
//            set.backgroundTintList = ColorStateList.valueOf(Color.parseColor(wallPaper.colors[4]))
//            download.backgroundTintList =
//                ColorStateList.valueOf(Color.parseColor(wallPaper.colors[2]))
//            save.backgroundTintList =
//                ColorStateList.valueOf(Color.parseColor(wallPaper.colors[3]))
//
//
//
//            progress.indeterminateTintList =
//                ColorStateList.valueOf(Color.parseColor(wallPaper.colors[2]))
        }
    }

    private fun setCenter() {
        Glide.with(this@DetailFragment)
            .asBitmap()
            .load(args.wallpaper.path)
            .centerInside()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    WallpaperManager.getInstance(context).setBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.d(TAG, "onLoadCleared: Clear")
                }
            })
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val dialogBinding = DialogBinding.inflate(inflater)
        dialogBinding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                dialogBinding.cropped.id -> selectedType = "Cropped"
                dialogBinding.scrolling.id -> selectedType = "Scroll"
            }
        }

        dialogBinding.setHome.setOnClickListener {
            when (selectedType) {
                "Cropped" -> setCropped()
                "Center" -> setCenter()
                "Scroll" -> setScroll()
            }
        }
        builder.setTitle("Set Wallpaper")
        builder.setView(dialogBinding.root)
        alertDialog = builder.show()
    }

    private fun setScroll() {
        alertDialog.dismiss()
        binding.progress.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            WallpaperManager.getInstance(context).setBitmap(bitmap)
            binding.progress.visibility = View.GONE
            Toast.makeText(context,"Wallpaper Applied",Toast.LENGTH_LONG).show()
        }

    }


    private fun setCropped() {
        alertDialog.dismiss()
        binding.progress.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = Bitmap.createBitmap(
                binding.screenShot.width,
                binding.screenShot.height,
                Bitmap.Config.ARGB_8888
            )
            val c = Canvas(bitmap)
            binding.screenShot.draw(c)
            WallpaperManager.getInstance(context).setBitmap(bitmap)
            binding.progress.visibility = View.GONE
            Toast.makeText(context,"Wallpaper Applied",Toast.LENGTH_LONG).show()
        }
    }

}