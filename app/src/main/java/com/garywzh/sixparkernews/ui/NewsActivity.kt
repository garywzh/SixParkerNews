package com.garywzh.sixparkernews.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.News
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        news = intent.getParcelableExtra("news")

        newsTitle.text = news.title
        source.text = news.source
        date.text = news.date


    }
}
