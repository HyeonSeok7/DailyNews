package com.hy.dailynews.feature.main.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.hy.dailynews.databinding.ItemBannerBinding
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.feature.main.home.holder.BannerHolder
import com.hy.dailynews.feature.main.home.holder.NewsListHolder
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.models.Newss
import com.hy.dailynews.utils.Constants

class HomeNewsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private val items = ArrayList<HomeModel>()
    private val bannerItems = ArrayList<HomeModel>()

    fun addModel(model: HomeModel) {
        items.add(model)
        notifyDataSetChanged()
    }

    fun bannerModel(model: HomeModel) {
        bannerItems.add(model)
        notifyDataSetChanged()
    }

    fun bannerClear() {
        bannerItems.clear()
        notifyDataSetChanged()
    }

    fun listClear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        items.let {
            return it[position].viewType!!
        }
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
            is BannerHolder   -> holder.bind(bannerItems)
            is NewsListHolder -> holder.bind(items[position])
        }
    }

    companion object {
        private val TAG = HomeNewsListAdapter::class.java.simpleName
    }

}