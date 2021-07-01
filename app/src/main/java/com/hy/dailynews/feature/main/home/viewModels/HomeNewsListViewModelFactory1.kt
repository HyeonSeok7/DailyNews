package com.hy.dailynews.feature.main.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hy.dailynews.feature.main.home.repositories.HomeRepository1

class HomeNewsListViewModelFactory1(private val repository1: HomeRepository1): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepository1::class.java).newInstance(repository1)
    }

}