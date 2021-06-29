package com.hy.dailynews.feature.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemBannerBinding
import com.hy.dailynews.databinding.ItemHomeTitleBinding
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.main.home.HomeBestNewsSliderAdapter
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> () {


    private val items: ArrayList<News> = ArrayList()
    var onClick: (News) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            Constants.ViewType.BANNER_TYPE    -> {
                val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BannerViewHolder(binding)
            }
            Constants.ViewType.TITLE_TYPE     -> {
                val binding = ItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeTitleViewHolder(binding)
            }
            Constants.ViewType.NEWS_LIST_TYPE -> {
                val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return NewsListViewHolder(binding)
            }
            else -> {
                val binding = ItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeTitleViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        items[position].itemType.let {
            return when(items[position].itemType) {
                Constants.ViewType.BANNER_TYPE    -> Constants.ViewType.BANNER_TYPE
                Constants.ViewType.TITLE_TYPE     -> Constants.ViewType.TITLE_TYPE
                Constants.ViewType.NEWS_LIST_TYPE -> Constants.ViewType.NEWS_LIST_TYPE
                else -> Constants.ViewType.TITLE_TYPE
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(holder) {
            is BannerViewHolder    -> holder.bind(item)
            is HomeTitleViewHolder -> homeTitleItemRows(holder, position)
            is NewsListViewHolder  -> newsListItemRows(holder, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = items.size

    private fun homeTitleItemRows(holder: HomeTitleViewHolder, position: Int) {}

    private fun newsListItemRows(holder: NewsListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    fun addItems(newItems: List<News>?) {
        Log.v(TAG, "newItems:$newItems")
        newItems?.let {
            it.forEach{ data ->
                data.itemType = Constants.ViewType.NEWS_LIST_TYPE
            }
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addBestItems(newItems: List<News>?) {
        Log.e(TAG,"bestNewItem:$newItems")
        newItems?.let {
            it.forEach { data ->
                data.itemType = Constants.ViewType.BANNER_TYPE
            }
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    private class BannerViewHolder (private val binding : ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        val mySliderAdapter = HomeBestNewsSliderAdapter(binding.root.context)
        fun bind(data: News) {
            data.let {
                binding.sliderView.apply {
                    setSliderAdapter(mySliderAdapter)
                    setIndicatorAnimation(IndicatorAnimationType.SWAP)
                    setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                    setSliderAnimationDuration(600)
                    setIndicatorAnimationDuration(600)
                    scrollTimeInSec = 3
                    startAutoCycle()
//                    mySliderAdapter.addItems(it as List<News>)
                }
            }
        }
    }

    private class HomeTitleViewHolder (private val binding : ItemHomeTitleBinding) : RecyclerView.ViewHolder(binding.root) {}

    private class NewsListViewHolder (private val binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.data = data
            }
        }
    }

    companion object {
        private val TAG = NewsListAdapter::class.java.simpleName
    }
}