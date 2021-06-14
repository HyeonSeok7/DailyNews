package com.example.dailynews.feature.main.home

import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.R
import com.example.dailynews.bindViewTitleImg
import com.example.dailynews.databinding.ItemNewsBinding
import com.prof.rssparser.Article
import java.text.SimpleDateFormat
import java.util.*

class HomeRVAdapter(private val articles: MutableList<Article>) : RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = articles[position]
        holder.apply {
            bind(item, View.OnClickListener {
                val articleView = WebView(itemView.context)

                articleView.apply {
                    settings.loadWithOverviewMode = true
                    settings.javaScriptEnabled = true
                    isHorizontalScrollBarEnabled = false
                    webChromeClient = WebChromeClient()
                    loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " +
                            "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + item.content, null, "utf-8", null)
                }
                val alertDialog = androidx.appcompat.app.AlertDialog.Builder(itemView.context).create()
                alertDialog.setTitle(item.title)
                alertDialog.setView(articleView)
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()

                (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()

            })
        }
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, listener:View.OnClickListener) {
            Log.i(TAG,"Article:$article")
            binding.apply {
                var pubDateString = article.pubDate

                try {
                    val sourceDataString = article.pubDate
                    val sourceSdf =  SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.KOREA)
                    if (sourceDataString != null) {
                        val date = sourceSdf.parse(sourceDataString)
                        if (date != null) {
                            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            pubDateString = sdf.format(date)
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG,"error:$e")
                }

                this.apply {
                    tvContents.text = article.title
                    bindViewTitleImg(ivThumbnailsImage, article)
                }

                itemView.setOnClickListener(listener)


            }
        }
    }

    companion object {
        private val TAG = HomeRVAdapter::class.java.simpleName
    }
}