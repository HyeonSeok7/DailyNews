package com.hy.dailynews.feature.main.home.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayout
import com.hy.dailynews.feature.main.home.repositories.HomeRepository1
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeNewsListViewModel1(private val repository: HomeRepository1) : ViewModel() {


    /* 테스트 List */
    private val _newsListItem = SingleLiveEvent<News>()
    val newsListItem: SingleLiveEvent<News>
        get() = _newsListItem

    private val _bannerNewsListItem = SingleLiveEvent<News>()
    val bannerNewsListItem: SingleLiveEvent<News>
        get() = _bannerNewsListItem

    private val _bannerNewsList = SingleLiveEvent<MutableList<News>>()
    val bannerNewsList: SingleLiveEvent<MutableList<News>>
        get() = _bannerNewsList

    fun updateBannerNewsData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getBannerAllNews().let { data ->
                Log.v(TAG, "updateBannerNewsData data:$data")
                bannerNewsList.postValue(data)
            }
        }
    }

    fun updateBannerNewsData1() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getBannerNews1(this@HomeNewsListViewModel1)
        }
    }

    fun updateNewsData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllNews(this@HomeNewsListViewModel1)
        }
    }

    companion object {
        private val TAG = HomeNewsListViewModel1::class.java.simpleName
    }
}