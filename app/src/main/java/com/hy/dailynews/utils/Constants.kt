package com.hy.dailynews.utils

class Constants {

    /* Parser Url */
    class GoogleRSS {
        companion object {
            /* 구글 뉴스 */
            const val BASE_URL   = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko" // 한국
            const val SEARCH_URL = "https://news.google.com/rss/search?"                // 검색
            //                    "q=신간&hl=ko&gl=KR&ceid=KR%3Ako/" -> '신간'을 검색했을 경우

            /* 헤드라인 */
            // 최신, 세계, 비즈니스, 과학/기술, 엔터테인먼트, 스포츠, 건강
            const val HOT_URL                 = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB?hl=ko&gl=KR&ceid=KR%3Ako"
            const val WORLD_URL               = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiRkNCQVNMZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlPQ0FRYUNnb0lMMjB2TURsdWJWOHFDZ29JRWdic2hManFzNFFvQUEqKggAKiYICiIgQ0JBU0Vnb0lMMjB2TURWcWFHY1NBbXR2R2dKTFVpZ0FQAVAB?hl=ko&gl=KR&ceid=KR%3Ako"
            const val BUSINESS_URL            = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiTkNCQVNOQW9JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlPQ0FRYUNnb0lMMjB2TURsek1XWXFFQW9PRWd6cnVZVHNwb2pyaTRqc2lxUW9BQSoqCAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVABUAE?hl=ko&gl=KR&ceid=KR%3Ako"
            const val SCIENCE_TECHNOLOGY_URL  = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiUENCQVNOZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlQQ0FRYUN3b0pMMjB2TUdabWR6Vm1LaEVLRHhJTjZyTzg3WldaTC1xNHNPeUlvQ2dBKioIAComCAoiIENCQVNFZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaWdBUAFQAQ?hl=ko&gl=KR&ceid=KR%3Ako"
            const val ENTERTAINMENT_URL       = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiVkNCQVNPZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlPQ0FRYUNnb0lMMjB2TURKcWFuUXFGZ29VRWhMc2w1VHRoTER0aFl6c25ianJxTHp0aXJnb0FBKioIAComCAoiIENCQVNFZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaWdBUAFQAQ?hl=ko&gl=KR&ceid=KR%3Ako"
            const val SPORTS_URL              = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiSkNCQVNNUW9JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlPQ0FRYUNnb0lMMjB2TURadWRHb3FEUW9MRWduc2lxVHRqNnpzdUtBb0FBKioIAComCAoiIENCQVNFZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaWdBUAFQAQ?hl=ko&gl=KR&ceid=KR%3Ako"
            const val HEALTH_URL              = "https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtdHZHZ0pMVWlnQVAB/sections/CAQiRkNCQVNMZ29JTDIwdk1EVnFhR2NTQW10dkdnSkxVaUlPQ0FRYUNnb0lMMjB2TUd0ME5URXFDZ29JRWdicXNiVHFzSlVvQUEqKggAKiYICiIgQ0JBU0Vnb0lMMjB2TURWcWFHY1NBbXR2R2dKTFVpZ0FQAVAB?hl=ko&gl=KR&ceid=KR%3Ako"
        }
    }

    /* Intent Extra Key */
    class ExtraKey {
        companion object {
            const val KEY_WEB_URL     = "webUrl"
            const val KEY_SITE_NAME   = "siteName"
        }
    }

    /* View Type Values */
    class ViewType {
        companion object {
            const val BANNER_TYPE    = 0
            const val TITLE_TYPE     = 1
            const val NEWS_LIST_TYPE = 2
        }
    }

}