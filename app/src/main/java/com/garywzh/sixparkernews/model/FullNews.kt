package com.garywzh.sixparkernews.model

/**
 * Created by garywzh on 2018/3/5.
 */

data class FullNews(
        val id: Int,
        val title: String,
        val source: String,
        val date: String,
        val commentCount: Int,

        val content: String,
        val editor: String,
        val flower: Int,
        val passing: Int,
        val egg: Int,
        val popularComments: List<Comment>? = null
)