package com.hy.dailynews.feature.main.home.repositories

import com.hy.dailynews.feature.main.home.RemoteNewsData
import com.hy.dailynews.utils.DataSource
import com.hy.dailynews.models.News
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val remoteNewsData: RemoteNewsData) : DataSource {

    override fun getBestAllNews(): Flow<News> {
        return remoteNewsData.getBestAllNews()
    }

    override fun getAllNews(): Flow<News> {
        return remoteNewsData.getAllNews()
    }

    companion object {
        private var instance: HomeRepository? = null

        fun getInstance(remoteNewData: RemoteNewsData) = instance ?: synchronized(this) {
            instance ?: instance ?: HomeRepository(remoteNewData).also { instance = it }
        }
    }
}