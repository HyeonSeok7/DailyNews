package com.hy.dailynews.feature.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeNewsListViewModelFactory(private val repository: HomeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepository::class.java).newInstance(repository)
    }

}