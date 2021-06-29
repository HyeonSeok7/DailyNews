package com.hy.dailynews.feature.main.home

import com.hy.dailynews.utils.listeners.DataSource
import com.hy.dailynews.models.News
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val remoteNewsData: RemoteNewsData) : DataSource {

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