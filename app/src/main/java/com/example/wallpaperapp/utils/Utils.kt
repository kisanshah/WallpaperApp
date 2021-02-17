package com.example.wallpaperapp.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class Utils {
    companion object {


        fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap? {
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.draw(c)
            return b
        }
    }
}