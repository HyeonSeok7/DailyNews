package com.hy.dailynews.feature.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hy.dailynews.R
import com.hy.dailynews.databinding.FragmentHome1Binding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.feature.main.NewsListAdapter
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants


class HomeFragment1 : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHome1Binding
    private lateinit var mAdapter: NewsListAdapter
    private lateinit var mContext: Context
    private lateinit var homeViewModel: HomeNewsListViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onRefresh() {
        mAdapter.clear()
//        homeViewModel.updateBestNewsData()
        homeViewModel.updateNewsData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome1Binding.inflate(inflater).apply {
            val homeListViewModelFactory = HomeNewsListViewModelFactory(HomeRepository(RemoteNewsData()))
            homeViewModel = ViewModelProvider(requireActivity(), homeListViewModelFactory).get(HomeNewsListViewModel::class.java)
        }

        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        setUpData()
        return binding.root
    }

    private fun loadNewsData() {
        homeViewModel.updateNewsData()
//        homeViewModel.updateBestNewsData()
    }

    private fun initRVLayout() {
        mAdapter = NewsListAdapter()
            .apply {
                setHasStableIds(true)
                onClick = this@HomeFragment1::startDetailNewsActivity
            }

        binding.rvNewsList.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpData() {
        loadNewsData()
        // 뉴스 세팅
        homeViewModel.newsList.observe(viewLifecycleOwner, { data ->
            mAdapter.clear()
            mAdapter.addItems(data)
        })
    }

    private fun startDetailNewsActivity(news: News) {
        startActivity(
            Intent(context, NewsDetailActivity::class.java)
            .apply {
                putExtra(Constants.ExtraKey.KEY_WEB_URL, news.url)
                putExtra(Constants.ExtraKey.KEY_SITE_NAME, news.siteName)
            })
    }


    companion object {
        private val TAG = HomeFragment1::class.java.simpleName

        fun newInstance() = HomeFragment1().apply {
            arguments = bundleOf()
        }
    }
}