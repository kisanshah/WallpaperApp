package com.example.wallpaperapp.views.fragments

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.DetailFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : BaseFragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override var bottomNavigationViewVisibility = View.GONE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailFragmentBinding.bind(view)

        bottomSheetBehavior =
            BottomSheetBehavior.from<LinearLayout>(binding.include.detailSheet)

        bottomSheetBehavior.peekHeight = 200
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Toast.makeText(context, newState.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Toast.makeText(context, slideOffset.toString(), Toast.LENGTH_LONG).show()
            }

        })
        binding.apply {
            val wallPaper = args.wallpaper
            Glide.with(this@DetailFragment)
                .asBitmap()
                .load(wallPaper.path)
                .centerCrop()
                .into(wallpaper)
            createdAt.text = wallPaper.created_at

            include.showHide.setOnClickListener {
                val rotation: Animation =
                    AnimationUtils.loadAnimation(context, R.anim.rotate)
                rotation.repeatCount = 2
                binding.include.save.startAnimation(rotation)
                Toast.makeText(context, "Show/Hide", Toast.LENGTH_LONG).show()
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        binding.setBtn.setOnClickListener {
            val bitmap = Bitmap.createBitmap(
                binding.screenShot.width,
                binding.screenShot.height,
                Bitmap.Config.ARGB_8888
            )
            val c = Canvas(bitmap)
            binding.screenShot.draw(c)
            WallpaperManager.getInstance(context).setBitmap(bitmap)
        }
    }

}