package com.garywzh.sixparkernews.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import com.garywzh.sixparkernews.PicassoImageGetter
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.FullNews
import com.garywzh.sixparkernews.model.News
import com.garywzh.sixparkernews.network.NetworkObj
import kotlinx.android.synthetic.main.activity_news.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NewsActivity : AppCompatActivity() {

    private lateinit var news: News
    private var fullNews: FullNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        news = intent.getParcelableExtra("news")
        Log.d("NewsActivity", news.toString())

        newsTitle.text = news.title
        source.text = news.source
        date.text = news.date

        loadFullNews()
    }

    private fun loadFullNews() {
        doAsync {
            fullNews = NetworkObj.getNewsDetail(news.id)
            Log.d("NewsActivity", fullNews.toString())
            uiThread {
                initFullNews()
            }
        }
    }

    private fun initFullNews() {
        fullNews?.let {
            date.text = it.date
            newsContent.text = Html.fromHtml(it.content, PicassoImageGetter(newsContent), null)
            editor.text = it.editor
            flower.text = it.flower.toString()
            passing.text = it.passing.toString()
            egg.text = it.egg.toString()
        }
    }
}
