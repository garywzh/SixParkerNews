package com.garywzh.sixparkernews.parser

import com.garywzh.sixparkernews.model.Comment
import com.garywzh.sixparkernews.model.FullNews
import org.jsoup.nodes.Document

/**
 * Created by garywzh on 2018/3/5.
 */
object FullNewsParser {

    fun parseDocForFullNews(id: Int, doc: Document): FullNews? {
        val commentCount = doc.body().child(2).child(0).child(0).child(0).child(2).child(1).text()
                .replace("【网友评论:", "").replace("条】", "").trim().toInt()

        val newsContent = doc.select("#newscontent").first()

        val title = newsContent.child(0).ownText()

        val sourceAndDate = newsContent.child(1).ownText().trim().split("于")

        val source = sourceAndDate[0].replace("新闻来源:", "").trim()

        val date = sourceAndDate[1].trim()

        val content = newsContent.child(2).toString()

        val trs = newsContent.child(4).child(0).children()

        val editor = trs[0].child(0).ownText().replace("网编：", "").trim()

        val votes = trs[0].child(1).select(".votenum")

        val flower = votes[0].text().replace("(", "").replace(")", "").toInt()
        var passing = 0
        val egg: Int
        if (votes.size < 3) {
            egg = votes[1].text().replace("(", "").replace(")", "").toInt()
        } else {
            passing = votes[1].text().replace("(", "").replace(")", "").toInt()
            egg = votes[2].text().replace("(", "").replace(")", "").toInt()
        }

        var comments: List<Comment>? = null
        if (trs.size >= 5) {
            comments = CommentListParser.parseComments(trs[4])
        }

        return FullNews(id, title, source, date, commentCount, content, editor, flower, passing, egg, comments)
    }
}