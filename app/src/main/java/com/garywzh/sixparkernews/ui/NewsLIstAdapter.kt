package com.garywzh.sixparkernews.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_list_item.*

/**
 * Created by garywzh on 2018/3/4.
 */
class NewsLIstAdapter(private val listener: (v: View?) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<News>? = null

    fun setData(data: List<News>) {
        this.data = data
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun getItemViewType(position: Int): Int = TYPE_NEWS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_NEWS) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_list_item, parent, false)
            view.setOnClickListener(listener)
            return NewsViewHolder(view)
        } else {
            throw RuntimeException("wrong view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.get(position)?.let {
            (holder as? NewsViewHolder)?.fillData(it)
        }
    }

    override fun getItemId(position: Int): Long = data?.get(position)?.id?.toLong() ?: 12345

    internal class NewsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun fillData(news: News) {
            title.text = news.title
            source.text = news.source
            date.text = news.date
            count.text = news.commentCount.toString()
            containerView.tag = news
        }
    }

    companion object {
        const val TYPE_NEWS = 0x01
    }
}