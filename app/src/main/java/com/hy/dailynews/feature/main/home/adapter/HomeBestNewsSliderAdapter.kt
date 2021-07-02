package com.hy.dailynews.feature.main.home.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hy.dailynews.databinding.ItemBannerViewBinding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import com.smarteist.autoimageslider.SliderViewAdapter

class HomeBestNewsSliderAdapter(private val context: Context) :
    SliderViewAdapter<HomeBestNewsSliderAdapter.SliderAdapterVH>() {

    private val items: ArrayList<News> = ArrayList()

    override fun getCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val binding = ItemBannerViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val item = items[position]
        viewHolder.apply {
            bind(item, View.OnClickListener {
                itemView.context.apply {
                    startActivity(Intent(context, NewsDetailActivity::class.java).apply {
                        putExtra(Constants.ExtraKey.KEY_WEB_URL, item.url)
                        putExtra(Constants.ExtraKey.KEY_SITE_NAME, item.siteName)
                    })
                }
            })
        }
    }

    fun addItem(newItem: News?) {
        newItem?.let { items.add(it) }
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<News>?) {
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class SliderAdapterVH(private val binding: ItemBannerViewBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(data: News, listener: View.OnClickListener) {
            binding.apply {
                this.data = data
                itemView.setOnClickListener(listener)
            }
        }
    }

    companion object {
        private val TAG = HomeBestNewsSliderAdapter::class.java.simpleName
    }
}