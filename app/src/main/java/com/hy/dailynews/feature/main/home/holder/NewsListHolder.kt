package com.hy.dailynews.feature.main.home.holder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListHolder (var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeModel) {
            binding.data = item.newsList
            binding.layoutRoot.setOnClickListener { view ->
                view.context.startActivity(Intent(view.context, NewsDetailActivity::class.java).apply {
                    putExtra(Constants.ExtraKey.KEY_WEB_URL, item.newsList?.url)
                    putExtra(Constants.ExtraKey.KEY_SITE_NAME, item.newsList?.siteName)
                })
            }
    }

    companion object {
        private val TAG = NewsListHolder::class.java.simpleName
    }
}