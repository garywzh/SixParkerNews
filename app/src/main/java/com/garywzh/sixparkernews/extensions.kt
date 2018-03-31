package com.garywzh.sixparkernews

import android.text.Spannable
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.view.View

fun Spannable.onImageClick(listener: (url: String) -> Unit) {
    for (span in getSpans(0, length, ImageSpan::class.java)) {
        val flags = getSpanFlags(span)
        val start = getSpanStart(span)
        val end = getSpanEnd(span)

        setSpan(object : URLSpan(span.source) {
            override fun onClick(widget: View) {
                listener(url)
            }
        }, start, end, flags)
    }
}