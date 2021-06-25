package com.hy.dailynews.feature.main.home

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.listeners.OnNewsClickListener

class HomeListAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    private lateinit var mOnClickListener: OnNewsClickListener


    fun addItems(newItems: List<News>?) {
        Log.v(TAG,"newItems:$newItems")
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    //    inner class ViewHolder (override val containerView: View): RecyclerView.ViewHolder(containerView)
    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {

            }
        }
    }

    companion object {
        private val TAG = HomeListAdapter::class.java.simpleName
    }

}