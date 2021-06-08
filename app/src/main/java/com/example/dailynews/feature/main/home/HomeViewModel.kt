package com.example.dailynews.feature.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {

    init {
        viewModelScope.launch {
            try {
            } catch (e: Exception) {
                Log.e(TAG,"error:$e")
            }
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}