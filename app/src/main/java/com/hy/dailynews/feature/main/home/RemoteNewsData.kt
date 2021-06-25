package com.hy.dailynews.feature.main.home

import android.util.Log
import com.hy.dailynews.utils.listeners.DataSource
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
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

class RemoteNewsData : DataSource {

    override fun getAllNews(): Flow<News> = flow {
        val time = measureTimeMillis {
            val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
            val newsAsync = mutableListOf<Deferred<News?>>()
            for (newsUrl in newsUrls) {
                CoroutineScope(Dispatchers.Default).async { getNewsFromUrl(newsUrl) }
                    .also { newsAsync.add(it) }
            }
            for (newsDeferred in newsAsync) {
                newsDeferred.await()?.let { emit(it) }
            }
        }
        Log.d(TAG, "걸린 시간: $time ms")
    }.flowOn(Dispatchers.IO)

    // 기사 url 로부터 News를 추출
    private fun getNewsFromUrl(newsUrl: String): News? {
        try {
            val doc by lazy {
                Jsoup.connect(newsUrl)
                    .timeout(3000)
                    .get()
                    .head()
            }
            val time = measureTimeMillis { doc }
            val title = doc.select("meta[property=og:title]").first()?.attr("content")
                ?: doc.select("title").first().html()
            val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
            val description = doc.select("meta[property=og:description]").first()?.attr("content")
                ?: doc.select("description").first()?.text()
                ?: doc.select("meta[name=description]").attr("content")
            Log.d("MyTime", "$title [${time}초]")
            return News(
                newsUrl,
                title,
                image,
                description
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


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


    companion object {

        private val TAG = RemoteNewsData::class.java.simpleName
        private var instance: RemoteNewsData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RemoteNewsData().also { instance = it }
            }

    }
}