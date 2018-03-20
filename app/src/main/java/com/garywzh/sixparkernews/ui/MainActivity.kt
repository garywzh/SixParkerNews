package com.garywzh.sixparkernews.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.News
import com.garywzh.sixparkernews.network.NetworkObj
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private lateinit var adapter: NewsLIstAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = NewsLIstAdapter(this)

        swipeRefreshLayout.setOnRefreshListener(this)

        swipeRefreshLayout.isRefreshing = true

        loadData()
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        doAsync {
            val list = NetworkObj.getNewsList()

            uiThread {
                list?.let {
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onClick(v: View?) {
        val news = v?.tag as News
        startActivity<NewsActivity>("news" to news)
    }
}
