package com.hy.dailynews.feature.main.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemBannerBinding
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.main.home.holder.BannerHolder
import com.hy.dailynews.feature.main.home.holder.NewsListHolder
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants

class HomeNewsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private val item = HomeModel()

    fun addModel(newsList: MutableList<News>) {
        newsList.forEach {
            item.newsList.add(it)
        }
        notifyItemChanged(item.newsList.size)
    }

    fun bannerModel(bannerList: MutableList<News>) {
        bannerList.forEach {
            item.bannerList.add(it)
        }
        notifyItemRangeChanged(0, 1)
//        notifyItemChanged(item.bannerList.size)
    }

    fun bannerClear() {
        item.bannerList.clear()
        notifyItemRemoved(0)
//        notifyDataSetChanged()
    }

    fun listClear() {
        item.newsList.clear()
        notifyItemRangeRemoved(1, item.newsList.size)
    }

    fun clear() {
        item.bannerList.clear()
        item.newsList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = item.newsList.size.plus(1)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) Constants.ViewType.BANNER_TYPE else Constants.ViewType.NEWS_LIST_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            Constants.ViewType.BANNER_TYPE    -> {
                val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BannerHolder(binding)
            }
            Constants.ViewType.NEWS_LIST_TYPE -> {
                val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewsListHolder(binding)
            }
            else -> {
                val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BannerHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerHolder   -> holder.bind(item.bannerList)
            is NewsListHolder -> holder.bind(item.newsList[position-1])
        }
    }

    companion object {
        private val TAG = HomeNewsListAdapter::class.java.simpleName
    }

}