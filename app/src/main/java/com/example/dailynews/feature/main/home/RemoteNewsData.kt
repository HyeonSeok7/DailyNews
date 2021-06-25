package com.example.dailynews.feature.main.home

import android.util.Log
import com.example.dailynews.Interface.DataSource
import com.example.dailynews.models.News
import com.example.dailynews.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.checkOffsetAndCount
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.system.measureTimeMillis

class RemoteNewsData : DataSource {

    override fun getAllNews(): Flow<News> = flow {
        val time = measureTimeMillis {
            val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
            val newAsync = mutableListOf<Deferred<News?>>()
            for (newsUrl in newsUrls) {
                CoroutineScope(Dispatchers.Default).async {  }
            }
        }
    }

    // rss 페이지에서 기사 url 추출
    fun getUrlFromRss(rssUrl: String): MutableList<String> {
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
            when(eventType) {

                XmlPullParser.START_TAG ->
                    if (parser.depth > 3 && parser.name=="link") {
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

//    // 기사 url 로부터 News를 추출
//    fun getNewsFromUrl(newsUrl: String): News? {
//
//    }

    companion object {

        private val TAG = RemoteNewsData::class.java.simpleName
        private var instance: RemoteNewsData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RemoteNewsData().also { instance = it }
            }

    }
}