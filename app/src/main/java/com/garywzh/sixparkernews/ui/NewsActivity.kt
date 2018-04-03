package com.garywzh.sixparkernews.ui

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import com.garywzh.sixparkernews.GlideImageGetter
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.FullNews
import com.garywzh.sixparkernews.model.News
import com.garywzh.sixparkernews.network.NetworkObj
import com.garywzh.sixparkernews.onImageClick
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
            uiThread {
                initFullNews()
            }
        }
    }

    private fun initFullNews() {
        fullNews?.let {
            date.text = it.date
            editor.text = it.editor
            flower.text = it.flower.toString()
            passing.text = it.passing.toString()
            egg.text = it.egg.toString()

            val content = Html.fromHtml(it.content, GlideImageGetter(newsContent), null) as Spannable
            content.onImageClick {
                bigImage.showImage(Uri.parse(it))
                bigImage.visibility = View.VISIBLE
            }

            newsContent.text = content
            newsContent.movementMethod = LinkMovementMethod.getInstance()

            initHotComments()
        }
    }

    private fun initHotComments() {
        fullNews?.popularComments?.let {
            for (comment in it) {
                val view = layoutInflater.inflate(R.layout.comment_list_item, null)

                view.findViewById<TextView>(R.id.author).text = comment.author
                view.findViewById<TextView>(R.id.commentDate).text = comment.date

                if (comment.toFloor != 0) {
                    val to = view.findViewById<TextView>(R.id.toFloor)
                    to.text = "回复${comment.toFloor}楼"
                    to.visibility = View.VISIBLE
                }

                view.findViewById<TextView>(R.id.content).text = comment.content
                view.findViewById<TextView>(R.id.thumbUp).text = "赞 ${comment.thumbUp}"

                container.addView(view)
            }
            container.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if (bigImage.visibility == View.VISIBLE) {
            bigImage.visibility = View.GONE
            return
        } else {
            super.onBackPressed()
        }
    }
}
