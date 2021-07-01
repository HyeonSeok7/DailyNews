package com.hy.dailynews.utils

import com.hy.dailynews.models.News
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAllNews(): Flow<News>
    fun getBestAllNews(): Flow<News>
}