package com.hy.dailynews.feature.main.home.repositories

import com.hy.dailynews.feature.main.home.RemoteNewsData
import com.hy.dailynews.utils.listeners.DataSource
import com.hy.dailynews.models.News
import com.hy.dailynews.models.Newss
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val remoteNewsData: RemoteNewsData) : DataSource {

    override fun getBestAllNews(): Flow<Newss> {
        return remoteNewsData.getBestAllNews()
    }

    override fun getAllNews(): Flow<Newss> {
        return remoteNewsData.getAllNews()
    }

    companion object {
        private var instance: HomeRepository? = null

        fun getInstance(remoteNewData: RemoteNewsData) = instance ?: synchronized(this) {
            instance ?: instance ?: HomeRepository(remoteNewData).also { instance = it }
        }
    }
}