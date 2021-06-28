package com.hy.dailynews.utils

class Constants {

    /* Parser Url */
    class GoogleRSS {
        companion object {
            // 구글 뉴스
            const val BASE_URL   = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko" // 한국
            const val SEARCH_URL = "https://news.google.com/rss/search?"                // 검색
            //                    "q=신간&hl=ko&gl=KR&ceid=KR%3Ako/"

            // 헤드라인
            const val HOT_URL    = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB?hl=ko&gl=KR&ceid=KR%3Ako"


        }
    }

    /* Intent Extra Key */
    class ExtraKey {
        companion object {
            const val KEY_WEB_URL     = "webUrl"
            const val KEY_SITE_NAME   = "siteName"
        }
    }

}