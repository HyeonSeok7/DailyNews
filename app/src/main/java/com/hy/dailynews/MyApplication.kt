package com.hy.dailynews

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = null
    }

    override fun onTerminate() {
        instance = null
        super.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }

    companion object {
        private val TAG = MyApplication::class.java.simpleName
        var instance: MyApplication? = null
    }
}