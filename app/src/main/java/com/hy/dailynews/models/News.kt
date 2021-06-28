package com.hy.dailynews.models

import androidx.room.Entity

@Entity
data class News(
    val url: String,
    val title: String,
    val image: String,
    val siteName: String,
    val description: String
)