package com.hy.dailynews.models

data class HomeModel(
    val bannerList: MutableList<News> = mutableListOf(),
    var newsList: MutableList<News> = mutableListOf()
)
