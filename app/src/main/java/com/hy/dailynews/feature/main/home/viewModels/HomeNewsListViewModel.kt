package com.hy.dailynews.feature.main.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hy.dailynews.feature.main.home.repositories.HomeRepository
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeNewsListViewModel(private val repository: HomeRepository) : ViewModel() {


    private val _newsList = SingleLiveEvent<MutableList<News>>()
    val newsList: SingleLiveEvent<MutableList<News>>
        get() = _newsList

    private val _bestNewsList = SingleLiveEvent<MutableList<News>>()
    val bestNewsList: SingleLiveEvent<MutableList<News>>
        get() = _bestNewsList

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress


    init {
        updateBestNewsData()
        updateNewsData()
    }

    fun updateBestNewsData() {
        _bestNewsList.value = mutableListOf()
//        CoroutineScope(Dispatchers.IO).launch {
        viewModelScope.launch {
            val data = repository.getBestAllNews()
            data.onCompletion { _progress.value = false }.collect {
                _bestNewsList.value = _bestNewsList.value?.apply { add(it) } ?: mutableListOf()
            }
        }
//        }
    }

    fun updateNewsData() {
        _progress.value = true
        _newsList.value = mutableListOf()
        viewModelScope.launch {
            val data = repository.getAllNews()
            data.onCompletion { _progress.value = false }.collect {
                _newsList.value = _newsList.value?.apply { add(it) } ?: mutableListOf(it)
            }
        }
    }

    companion object {
        private val TAG = HomeNewsListViewModel::class.java.simpleName
    }
}