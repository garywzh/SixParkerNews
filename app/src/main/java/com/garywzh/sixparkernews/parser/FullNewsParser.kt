package com.garywzh.sixparkernews.parser

import com.garywzh.sixparkernews.model.Comment
import com.garywzh.sixparkernews.model.FullNews
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * Created by garywzh on 2018/3/5.
 */
object FullNewsParser {

    fun parseDocForFullNews(id: Int, doc: Document): FullNews? {
        val newsContent = doc.select("#newscontent").first()

        val title = newsContent.child(0).ownText()

        val source = newsContent.child(1).ownText()

        val shownewsc = newsContent.child(2)

        val content = shownewsc.text()

        val elements = newsContent.child(4).child(0).children()

        val editor = elements[0].child(0).ownText()

        val flower = elements[0]

        val comments = parseComments(elements[2])

//        return FullNews(id, title, source, date, commentCount, content, editor, flower, passing, egg, comments)

    }

    fun parseEditor(): String {

    }

    fun parseComments(ele: Element): List<Comment> {

        val elements = ele.select(".reply_list_div")


    }
}