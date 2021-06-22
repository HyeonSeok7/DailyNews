package com.example.dailynews.utils

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ThumbnailsParser {

    companion object {

        var tempString: String = ""

        fun thumbnailsParser(link: String?) : String {
            Log.v(TAG, "thumbnailsParser link:$link")
            CoroutineScope(Dispatchers.IO).launch {
                val doc = Jsoup.connect(link).get().head()
                val thumbnail = doc.select("meta[property=og:image]").first().attr("content")
                Log.e(TAG,"thumbnail:$thumbnail")
                tempString = thumbnail
            }
            return tempString
        }


        private val TAG = ThumbnailsParser::class.java.simpleName
    }
}