package com.garywzh.sixparkernews.network

import android.util.Log
import com.garywzh.sixparkernews.model.Comment
import com.garywzh.sixparkernews.model.FullNews
import com.garywzh.sixparkernews.model.News
import com.garywzh.sixparkernews.parser.CommentListParser
import com.garywzh.sixparkernews.parser.FullNewsParser
import com.garywzh.sixparkernews.parser.NewsListParser
import com.garywzh.sixparkernews.parser.Parser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created by garywzh on 2018/3/4.
 */
object NetworkObj {
    private const val NEWS_LIST: String = "http://news.6parker.com/newspark/index.php?type=1"
    private const val NEWS_DETAIL_PREFIX: String = "http://news.toutiaoabc.com/newspark/view.php?app=news&act=view&nid="
    private const val COMMENT_LIST_PREFIX: String = "https://news.toutiaoabc.com/newspark/index.php?act=newsreply&nid="

    private val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor
            .Logger { message -> Log.d("Http info: ", message) })
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    fun getNewsList(): List<News>? {
        val request = Request.Builder().url(NEWS_LIST).build()

        val response = client.newCall(request).execute()

        var newsList: List<News>? = null

        response.body()?.let {
            val html = it.string()
            val doc = Parser.toDoc(html)
            newsList = NewsListParser.parseDocForNewsList(doc)
        }

        return newsList
    }

    fun getNewsDetail(id: Int): FullNews? {
        val request = Request.Builder().url(NEWS_DETAIL_PREFIX + id).build()

        val response = client.newCall(request).execute()

        var fullNews: FullNews? = null

        response.body()?.let {
            val html = it.string()
            val doc = Parser.toDoc(html)
            fullNews = FullNewsParser.parseDocForFullNews(id, doc)
        }

        return fullNews
    }

    fun getCommentList(id: Int): List<Comment>? {
        val request = Request.Builder().url(COMMENT_LIST_PREFIX + id).build()

        val response = client.newCall(request).execute()

        var newsList: List<Comment>? = null

        response.body()?.let {
            val html = it.string()
            val doc = Parser.toDoc(html)
            newsList = CommentListParser.parseDocForCommentList(doc)
        }

        return newsList
    }
}