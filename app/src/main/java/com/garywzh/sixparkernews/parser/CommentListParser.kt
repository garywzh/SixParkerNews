package com.garywzh.sixparkernews.parser

import com.garywzh.sixparkernews.model.Comment
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object CommentListParser {
    fun parseDocForCommentList(doc: Document): List<Comment> = parseComments(doc.select("#reply_list_all")[0])

    fun parseComments(ele: Element): List<Comment> {
        val elements = ele.select(".reply_list_div")

        val list = ArrayList<Comment>(elements.size)
        for (e in elements) {
            list.add(parseComment(e))
        }

        list.reverse()
        return list
    }

    private fun parseComment(ele: Element): Comment {
        val id = ele.id().split("_").last().toInt()

        var pre = 0
        var floor = 0
        val eles = ele.child(0).children()

        if (eles.size > 4) {
            pre = 1
            floor = eles[0].select("a")[0].text().toInt()
        }

        val author = eles[0 + pre].child(1).text()

        val date = eles[3 + pre].ownText().trim()

        val elements = ele.child(1).select(".r_reply_a")

        var toFloor = 0
        if (elements.size >= 1) {
            toFloor = elements[0].child(0).text().toInt()
        }

        var content = ele.child(1).ownText().trim()

        if (content.isEmpty()) {
            content = "此评论已被删除"
        }

        val thumbUpText = ele.child(2).child(0).child(0).child(1).text()

        val thumbUp = if (thumbUpText.isNotEmpty()) thumbUpText.toInt() else 0

        return Comment(id, author, date, content, floor, toFloor, thumbUp)
    }
}