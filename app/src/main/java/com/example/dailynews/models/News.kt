package com.example.dailynews.models

import androidx.room.Entity

@Entity
data class News(
    val url: String,
    val title: String,
    val description: String
)