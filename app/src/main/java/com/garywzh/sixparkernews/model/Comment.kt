package com.garywzh.sixparkernews.model

/**
 * Created by garywzh on 2018/3/4.
 */
data class Comment (
        val author: String,
        val authorLink:String,
        val date: String,
        val content:String,
        val floor: Int,
        val toFloor: Int,
        val thumbUp:Int
)