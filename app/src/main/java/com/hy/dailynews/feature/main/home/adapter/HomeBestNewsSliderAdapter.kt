package com.hy.dailynews.feature.main.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hy.dailynews.databinding.ItemBannerViewBinding
import com.hy.dailynews.models.News
import com.smarteist.autoimageslider.SliderViewAdapter

class HomeBestNewsSliderAdapter(private val context: Context) :
    SliderViewAdapter<HomeBestNewsSliderAdapter.SliderAdapterVH>() {

    private val items: ArrayList<News> = ArrayList()

    override fun getCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val binding = ItemBannerViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        val item = items[position]
        viewHolder?.bind(item)
    }

    fun addItem(newItem: News?) {
        newItem?.let {
            items.add(it)
            notifyDataSetChanged()
        }
    }

    fun addItems(newItems: List<News>?) {
        Log.v(TAG, "newItems:$newItems")
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
        fun bind(data: News) {
            binding.apply {
                this.data = data
            }
        }
    }

    companion object {
        private val TAG = HomeBestNewsSliderAdapter::class.java.simpleName
    }
}