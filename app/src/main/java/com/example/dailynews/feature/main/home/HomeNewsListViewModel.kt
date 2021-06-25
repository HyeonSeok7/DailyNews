package com.example.dailynews.feature.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.models.News
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeNewsListViewModel(private val repository: HomeRepository) : ViewModel() {


    private val _newsList = MutableLiveData<MutableList<News>>()
    val newList: LiveData<MutableList<News>>
        get() = _newsList

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get()= _progress


    init {
        updateNewsData()
    }

    fun updateNewsData() {
        _progress.value = true
        _newsList.value = mutableListOf()
        viewModelScope.launch {
            val data = repository.getAllNews()
            data.onCompletion { _progress.value = false }.collect { _newsList.value = _newsList.value?.apply { add(it) } ?: mutableListOf(it) }
        }
    }


}