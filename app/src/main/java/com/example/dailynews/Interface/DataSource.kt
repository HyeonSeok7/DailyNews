package com.example.dailynews.Interface

import com.example.dailynews.models.News
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAllNews(): Flow<News>
}