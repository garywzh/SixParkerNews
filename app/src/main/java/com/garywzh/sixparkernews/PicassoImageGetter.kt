package com.garywzh.sixparkernews

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Log
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PicassoImageGetter(private val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        val drawable = NetworkDrawable()
        val target = NetworkTarget(drawable, textView)

        Picasso.get().load(source)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(target)

        return drawable
    }

    private class NetworkTarget(private val networkDrawable: NetworkDrawable, private val textView: TextView) : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable) {
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
            val drawable = BitmapDrawable(bitmap)
            val ratio: Float = bitmap.height.toFloat() / bitmap.width.toFloat()
            val drawableHeight = (textView.width * ratio).toInt()
            drawable.setBounds(0, 0, textView.width, drawableHeight)
            networkDrawable.drawable = drawable
            networkDrawable.setBounds(0, 0, textView.width, drawableHeight)
            textView.text = textView.text
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Log.d("ImageGetter", "load image failed")
        }
    }

    private class NetworkDrawable : BitmapDrawable() {
        var drawable: BitmapDrawable? = null

        override fun draw(canvas: Canvas?) {
            drawable?.draw(canvas)
        }
    }
}