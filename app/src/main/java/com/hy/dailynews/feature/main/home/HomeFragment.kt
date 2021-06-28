package com.hy.dailynews.feature.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hy.dailynews.databinding.FragmentHomeBinding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants


class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: HomeListAdapter
    private lateinit var mContext: Context
    private lateinit var homeViewModel: HomeNewsListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onRefresh() {
        mAdapter.clear()
        homeViewModel.updateNewsData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater).apply {
            val homeListViewModelFactory = HomeNewsListViewModelFactory(HomeRepository(RemoteNewsData()))
            homeViewModel = ViewModelProvider(requireActivity(), homeListViewModelFactory).get(HomeNewsListViewModel::class.java)
        }

        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        setUpData()
        return binding.root
    }

    private fun initRVLayout() {
        mAdapter = HomeListAdapter(mContext)
            .apply {
                setHasStableIds(true)
                onClick = this@HomeFragment::startDetailNewsActivity
            }

        binding.rvNewsList.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setUpData() {
        // 뉴스 세팅
        homeViewModel.newsList.observe(viewLifecycleOwner, { data ->
            mAdapter.clear()
            mAdapter.addItems(data)
        })
        homeViewModel.progress.observe(viewLifecycleOwner, { bar ->
            binding.layoutRefresh.isRefreshing = bar
        })
    }

    private fun startDetailNewsActivity(news: News) {
        startActivity(Intent(context, NewsDetailActivity::class.java)
            .apply {
                putExtra(Constants.ExtraKey.KEY_WEB_URL, news.url)
                putExtra(Constants.ExtraKey.KEY_SITE_NAME, news.siteName)
            })
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }

    }
}
