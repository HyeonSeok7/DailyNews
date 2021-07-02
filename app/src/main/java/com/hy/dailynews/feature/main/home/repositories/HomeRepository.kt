package com.hy.dailynews.feature.main.home.repositories

import android.util.Log
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import com.hy.dailynews.utils.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.system.measureTimeMillis

class HomeRepository : DataSource {

    // 배너 뉴스
    fun getBannerAllNews(): MutableList<News> {
        val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
        val bannerNewsList = mutableListOf<News>()
        newsUrls.filterIndexed { index, _ -> index < 5 }.forEach {
            getNewsFromUrl(it)?.let { news ->
                bannerNewsList.add(news)
            }
        }
        return bannerNewsList
    }

    // 최신 뉴스
    override fun getAllNews(): Flow<News> = flow {
        val time = measureTimeMillis {
            val newsUrls = getUrlFromRss(Constants.GoogleRSS.HOT_URL)
            val newsAsync = mutableListOf<Deferred<News?>>()

            for (newsUrl in newsUrls) {
                CoroutineScope(Dispatchers.IO).async { getNewsFromUrl(newsUrl) }
                    .also { newsAsync.add(it) }
            }

            newsAsync.forEach { it -> it.await()?.let { emit(it) } }

        }
        Log.d(TAG,"걸린 시간: $time ms")
    }.flowOn(Dispatchers.IO)

    // rss 페이지에서 기사 url 추출
    private fun getUrlFromRss(rssUrl: String): MutableList<String> {
        val newsUrls = mutableListOf<String>()

        // XML 파싱을 위한 XML Parser
        val parser = Jsoup.connect(rssUrl).get().let { document ->
            val rssXML = document.html()
            val rssXmlStrReader = StringReader(rssXML)
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware }
            factory.newPullParser().apply { setInput(rssXmlStrReader) }
        }

        // 기사 url 추출
        var eventType = parser.eventType
        var isNewsAddress = false
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG ->
                    if (parser.depth > 3 && parser.name == "link") {
                        isNewsAddress = true
                    }
                XmlPullParser.TEXT ->
                    if (isNewsAddress) {
                        newsUrls.add(parser.text)
                        isNewsAddress = false
                    }
            }
            eventType = parser.next()
        }
        return newsUrls
    }

    // 기사 url 로부터 News 추출
    private fun getNewsFromUrl(newsUrl: String): News? {
        try {
            val doc by lazy {
                Jsoup.connect(newsUrl)
//                    .timeout(1500)
                    .get()
                    .head()
            }
            val title = doc.select("meta[property=og:title]").first()?.attr("content")
                ?: doc.select("title").first().html()
            val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
            val siteName = doc.select("meta[property=og:site_name]").first()?.attr("content") ?: ""
            val description = doc.select("meta[property=og:description]").first()?.attr("content")
                ?: doc.select("description").first()?.text()
                ?: doc.select("meta[name=description]").attr("content")

            return News(
                newsUrl,
                title,
                image,
                siteName,
                description,
                )

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    //    override fun getBannerAllNews1(): Flow<News> = flow {
//        val time = measureTimeMillis {
//            val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
//            val newsAsync = mutableListOf<Deferred<News?>>()
//
//            for (newsUrl in newsUrls) {
//                CoroutineScope(Dispatchers.IO).async { getNewsFromUrl(newsUrl) }
//                    .also { while (newsAsync.size < 6) newsAsync.add(it) }
//            }
//
//            newsAsync.filterIndexed { index, deferred -> index < 5 }.forEach { it.await()?.let { news -> emit(news) } }
//
////            newsAsync.forEach { it.await()?.let { emit(it) } }
//        }
//        Log.d(TAG,"배너 걸린 시간: $time ms")
//    }.flowOn(Dispatchers.IO)


    companion object {
        private val TAG = HomeRepository::class.java.simpleName
    }

}