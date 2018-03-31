package com.garywzh.sixparkernews

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class GlideImageGetter(private val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        val drawable = NetworkDrawable()
        val target = NetworkTarget(drawable, textView)

        Glide.with(textView.context).asBitmap().load(source).into(target)

        return drawable
    }

    private class NetworkTarget(private val networkDrawable: NetworkDrawable, private val textView: TextView) : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            val drawable = BitmapDrawable(resource)
            val ratio: Float = resource.height.toFloat() / resource.width.toFloat()
            val drawableHeight = (textView.width * ratio).toInt()
            drawable.setBounds(0, 0, textView.width, drawableHeight)
            networkDrawable.drawable = drawable
            networkDrawable.setBounds(0, 0, textView.width, drawableHeight)
            textView.text = textView.text
        }
    }

    private class NetworkDrawable : BitmapDrawable() {
        var drawable: BitmapDrawable? = null

        override fun draw(canvas: Canvas?) {
            drawable?.draw(canvas)
        }
    }
}