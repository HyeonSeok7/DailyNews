package com.hy.dailynews.feature.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hy.dailynews.databinding.FragmentHomeBinding
import com.hy.dailynews.feature.main.home.adapter.HomeNewsListAdapter
import com.hy.dailynews.feature.main.home.repositories.HomeRepository
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModel
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModelFactory
import com.hy.dailynews.models.News

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: HomeNewsListAdapter
    private lateinit var homeNewsListViewModel: HomeNewsListViewModel

    override fun onRefresh() {
        mAdapter.bannerClear()
        mAdapter.listClear()
        homeNewsListViewModel.updateBannerNewsData()
        homeNewsListViewModel.updateNewsData()
        binding.layoutRefresh.isRefreshing = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater).apply {
            val homeListModelFactory = HomeNewsListViewModelFactory(HomeRepository())
            homeNewsListViewModel = ViewModelProvider(requireActivity(), homeListModelFactory).get(
                HomeNewsListViewModel::class.java
            )
        }
        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        initData()
        homeNewsListViewModel.updateBannerNewsData()
        homeNewsListViewModel.updateNewsData()
        return binding.root
    }

    private fun initRVLayout() {
        mAdapter = HomeNewsListAdapter().apply {
            setHasStableIds(true)
        }
        binding.rvNewsList.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initData() {

        homeNewsListViewModel.bannerNewsList.observe(viewLifecycleOwner, { data: MutableList<News> ->
            mAdapter.bannerClear()
            mAdapter.bannerModel(data)
        })

        homeNewsListViewModel.newsList.observe(viewLifecycleOwner, { data: MutableList<News> ->
            mAdapter.listClear()
            mAdapter.addModel(data)
        })

        homeNewsListViewModel.progress.observe(viewLifecycleOwner, { progress ->
            binding.layoutRefresh.isRefreshing = progress
        })
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }
    }
}