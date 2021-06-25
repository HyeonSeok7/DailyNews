package com.hy.dailynews.feature.main.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.R
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.ArticleDetailActivity
import com.hy.dailynews.utils.Constants
import com.hy.dailynews.utils.ThumbnailsParser
import com.prof.rssparser.Article
import java.util.*
import kotlin.collections.ArrayList

class HomeRVAdapter : RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {

    private val articles: MutableList<Article> = ArrayList()
    private var imgUrl: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.v(TAG,"onCreateViewHolder")
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder")
        val item = articles[position]
        holder.apply {

            bind(item) {
                itemView.context.apply {
                    val intent = Intent(this, ArticleDetailActivity::class.java)
                    intent.putExtra(Constants.ExtraKey.KEY_WEB_URL, item.link)
                    intent.putExtra(Constants.ExtraKey.KEY_SOURCE_NAME, item.sourceName)
                    startActivity(intent)
                }
//                val articleView = WebView(itemView.context)
//                articleView.apply {
//                    settings.loadWithOverviewMode = true
//                    settings.javaScriptEnabled = true
//                    isHorizontalScrollBarEnabled = false
//                    webChromeClient = WebChromeClient()
//                    loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " +
//                            "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + item.content, null, "utf-8", null)
//                }
            }
        }
    }

    override fun getItemCount() = articles.size

    fun addItems(newItems: List<Article>) {
        Log.e(TAG,"newItems:$newItems")
        articles.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, listener:View.OnClickListener) {
            Log.v(TAG,"Article:$article")
            binding.apply {
//                this.data = article
//                this.imgUrl = ThumbnailsParser.thumbnailsParser(article.link)
//                this.imgUrl = ThumbnailsParser.thumbnailsParser(article.link)
                itemView.setOnClickListener(listener)

//                bindViewTitleImg(this.ivThumbnailsImage, ThumbnailsParser.thumbnailsParser(article.link))
//                var pubDateString = article.pubDate
//
//                try {
//                    val sourceDataString = article.pubDate
//                    val sourceSdf =  SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.KOREA)
//                    if (sourceDataString != null) {
//                        val date = sourceSdf.parse(sourceDataString)
//                        if (date != null) {
//                            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
//                            pubDateString = sdf.format(date)
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.e(TAG,"error:$e")
//                }
            }
        }
    }

    companion object {
        private val TAG = HomeRVAdapter::class.java.simpleName
    }
}