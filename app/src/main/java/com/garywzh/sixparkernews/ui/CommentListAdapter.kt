package com.garywzh.sixparkernews.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garywzh.sixparkernews.R
import com.garywzh.sixparkernews.model.Comment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.comment_list_item.*

class CommentListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Comment>? = null

    override fun getItemCount(): Int = data?.size ?: 0

    override fun getItemViewType(position: Int): Int = TYPE_COMMENT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_COMMENT) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_list_item, parent, false)
            return CommentViewHolder(view)
        } else {
            throw RuntimeException("wrong view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.get(position)?.let {
            (holder as? CommentViewHolder)?.fillData(it)
        }
    }

    override fun getItemId(position: Int): Long = data?.get(position)?.id?.toLong() ?: 0

    internal class CommentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun fillData(comment: Comment) {
            floor.text = "[${comment.floor}楼] "
            floor.visibility = View.VISIBLE

            author.text = comment.author
            commentDate.text = comment.date

            if (comment.toFloor != 0) {
                toFloor.text = "回复${comment.toFloor}楼"
                toFloor.visibility = View.VISIBLE
            } else {
                toFloor.visibility = View.GONE
            }

            content.text = comment.content
            thumbUp.text = "${comment.thumbUp} 赞"
        }
    }

    companion object {
        const val TYPE_COMMENT = 0x01
    }
}