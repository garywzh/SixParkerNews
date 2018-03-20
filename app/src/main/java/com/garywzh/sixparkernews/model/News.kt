package com.garywzh.sixparkernews.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by garywzh on 2018/3/4.
 */

@Parcelize
data class News(
        val id: Int,
        val title: String,
        val source: String,
        val date: String,
        val commentCount: Int
) : Parcelable