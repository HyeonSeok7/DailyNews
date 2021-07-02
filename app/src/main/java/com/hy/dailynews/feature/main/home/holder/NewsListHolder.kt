package com.hy.dailynews.feature.main.home.holder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: News) {
        binding.data = item
        binding.layoutRoot.setOnClickListener { view ->
            view.context.startActivity(Intent(view.context, NewsDetailActivity::class.java).apply {
                putExtra(Constants.ExtraKey.KEY_WEB_URL, item.url)
                putExtra(Constants.ExtraKey.KEY_SITE_NAME, item.siteName)
            })
        }
    }

    companion object {
        private val TAG = NewsListHolder::class.java.simpleName
    }
}