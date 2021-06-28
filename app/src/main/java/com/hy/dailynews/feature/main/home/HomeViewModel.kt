package com.hy.dailynews.feature.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hy.dailynews.utils.Constants
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class HomeViewModel: ViewModel() {

    private lateinit var articleListLive: MutableLiveData<Channel>

    private val _rssChannel = MutableLiveData<Channel>()
    val rssChannel: LiveData<Channel>
        get() = _rssChannel

    private val okHttpClient by lazy {
        OkHttpClient()
    }

    fun fetchFeed(parser: Parser) {
        Log.e(TAG, "fetchFeed:$parser")
        viewModelScope.launch {
            try {
                val channel = parser.getChannel(Constants.GoogleRSS.BASE_URL)
                channel.articles
            } catch (e: Exception) {
                e.printStackTrace()
                _rssChannel.postValue(Channel(null, null, null, null, null, null, mutableListOf()))
            }
        }
    }

    fun fetchForUrlAndParseRawData(url: String) {
        Log.d(TAG,"fetchForUrlAndParseRawData, url:$url")
        val parser = Parser.Builder().build()

        viewModelScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            val result = okHttpClient.newCall(request).execute()
            val raw = runCatching { result.body?.string() }.getOrNull()
            if (raw == null) {
               Log.d(TAG, "raw data is null")
            } else {
                Log.d(TAG,"raw data is not null, $raw")
                val channel = parser.parse(raw)
                _rssChannel.postValue(channel)
            }
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}