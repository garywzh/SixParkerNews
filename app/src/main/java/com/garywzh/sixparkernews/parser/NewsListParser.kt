package com.garywzh.sixparkernews.parser

import android.util.Log
import com.garywzh.sixparkernews.model.News
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * Created by garywzh on 2018/3/4.
 */
object NewsListParser {
    fun parseDocForNewsList(doc: Document): List<News> {
        val list = doc.select("#d_list")

        val elements = list.select("li")

        val result = ArrayList<News>(elements.size)

        for (ele in elements) {
            parseLiForNews(ele)?.let {
                result.add(it)
            }
        }

        Log.d("parseNews", "news count: ${result.size}")
        return result
    }

    private fun parseLiForNews(li: Element): News? = try {
        val elements = li.select("a")

        val id = parseId(elements[0])
        val title = parseTitle(elements[0])

        val count = if (elements.size > 1) parseCount(elements[1]) else 0

        val mdyDate = li.select("i")[0].text().split("/")
        val date = "20${mdyDate[2]}-${mdyDate[0]}-${mdyDate[1]}"

        val source = parseSource(li)

        News(id, title, source, date, count)
    } catch (e: NumberFormatException) {
        Log.d("parseNews", "skip ad " + e.message)
        null
    }

    private fun parseId(a: Element): Int = a.attr("href").substringAfterLast("nid=").toInt()

    private fun parseTitle(a: Element): String = a.ownText()

    private fun parseSource(li: Element): String =
            Regex(".+?(?=\\()").find(li.ownText())?.value.orEmpty().replace(" ", "").replace("-", "")

    private fun parseCount(a: Element): Int = a.text().toInt()
}