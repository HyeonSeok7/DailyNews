package com.hy.dailynews.feature.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.hy.dailynews.R
import com.hy.dailynews.databinding.ItemNewsBinding
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.listeners.OnNewsClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeListAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    private lateinit var mOnClickListener: OnNewsClickListener

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

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            bind(item)
        }
    }

    //    inner class ViewHolder (override val containerView: View): RecyclerView.ViewHolder(containerView)
    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.data = data
            }

        }
    }

    companion object {
        private val TAG = HomeListAdapter::class.java.simpleName
    }

}