package com.garywzh.sixparkernews.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.network.NetworkObj
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CommentsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var adapter: CommentListAdapter
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        id = intent.getIntExtra("id", 0)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CommentListAdapter()
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true

        loadData()
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        doAsync {
            val list = NetworkObj.getCommentList(id)

            uiThread {
                list?.let {
                    adapter.data = it
                    adapter.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}
