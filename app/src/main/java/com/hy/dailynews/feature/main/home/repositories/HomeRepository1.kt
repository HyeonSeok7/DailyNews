package com.hy.dailynews.feature.main.home.repositories

import android.util.Log
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModel1
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class HomeRepository1 {

    private lateinit var homeNewsListViewModel: HomeNewsListViewModel1

    fun getBannerAllNews(): MutableList<News> {
        val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
        Log.v(TAG,"getBannerAllNews newsUrls:$newsUrls")
        val bannerNewsList = mutableListOf<News>()
        for (newsUrl in newsUrls) {
            getNewsFromUrl(newsUrl)?.let { bannerNewsList.add(it) }
        }
        Log.e(TAG,"bannerNewsList:$bannerNewsList")
        return bannerNewsList
    }

    fun getBannerNews1(viewModel: HomeNewsListViewModel1) {
        homeNewsListViewModel = viewModel
        val newsUrls = getUrlFromRss(Constants.GoogleRSS.BASE_URL)
        for (newsUrl in newsUrls) {
            getNewsFromUrl(newsUrl)?.let { data -> homeNewsListViewModel.bannerNewsListItem.postValue(data) }
        }
    }

    fun getAllNews(viewModel: HomeNewsListViewModel1) {
        homeNewsListViewModel = viewModel
        val newsUrls = getUrlFromRss(Constants.GoogleRSS.HOT_URL)
        for (newsUrl in newsUrls) {
            getNewsFromUrl(newsUrl)?.let { data -> homeNewsListViewModel.newsListItem.postValue(data) }
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

    companion object {
        private val TAG = HomeRepository1::class.java.simpleName
    }

}