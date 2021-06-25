package com.hy.dailynews.utils.listeners

import com.hy.dailynews.models.News
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAllNews(): Flow<News>
}