package com.hy.dailynews.utils

class Constants {

    /* Parser Url */
    class GoogleRSS {
        companion object {
            const val BASE_URL = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
            const val SEARCH_URL = "https://news.google.com/rss/search?"
            //                    "q=신간&hl=ko&gl=KR&ceid=KR%3Ako/"
        }
    }

    /* Intent Extra Key */
    class ExtraKey {
        companion object {
            const val KEY_WEB_URL     = "webUrl"
            const val KEY_SOURCE_NAME = "sourceName"
        }
    }

}