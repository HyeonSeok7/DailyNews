package com.hy.dailynews.utils.listeners

import com.hy.dailynews.models.Newss
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAllNews(): Flow<Newss>
    fun getBestAllNews(): Flow<Newss>
}