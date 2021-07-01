package com.hy.dailynews.feature.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hy.dailynews.databinding.FragmentHome1Binding
import com.hy.dailynews.feature.main.home.adapter.HomeNewsListAdapter
import com.hy.dailynews.feature.main.home.repositories.HomeRepository1
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModel1
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModelFactory1
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants

class HomeFragment1 : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHome1Binding
    private lateinit var mAdapter: HomeNewsListAdapter
    private lateinit var homeNewsListViewModel: HomeNewsListViewModel1

    override fun onRefresh() {
        mAdapter.bannerClear()
        mAdapter.listClear()
        initRVLayout()
        homeNewsListViewModel.updateBannerNewsData()
//        homeNewsListViewModel.updateBannerNewsData1()
        homeNewsListViewModel.updateNewsData()
        binding.layoutRefresh.isRefreshing = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome1Binding.inflate(inflater).apply {
            val homeListModelFactory = HomeNewsListViewModelFactory1(HomeRepository1())
            homeNewsListViewModel = ViewModelProvider(requireActivity(), homeListModelFactory).get(
                HomeNewsListViewModel1::class.java
            )
        }
        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        initData()
        homeNewsListViewModel.updateBannerNewsData()
//        homeNewsListViewModel.updateBannerNewsData1()
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
        mAdapter.addModel(HomeModel(Constants.ViewType.BANNER_TYPE, null))
    }

    private fun initData() {

        homeNewsListViewModel.bannerNewsList.observe(viewLifecycleOwner, { data ->
            Log.v(TAG, "bannerList:$data")
            mAdapter.bannerClear()
            data.forEach { banner ->
                mAdapter.bannerModel(HomeModel(Constants.ViewType.BANNER_TYPE, banner))
            }
        })

//        homeNewsListViewModel.newsListItem.observe(viewLifecycleOwner, { data ->
//            mAdapter.bannerClear()
//            mAdapter.bannerModel(HomeModel(Constants.ViewType.BANNER_TYPE, data))
//
//        })

        homeNewsListViewModel.newsListItem.observe(viewLifecycleOwner, { data ->
            mAdapter.addModel(HomeModel(Constants.ViewType.NEWS_LIST_TYPE, data))
        })

//        homeNewsListViewModel.progress.observe(viewLifecycleOwner, { progress ->
//            binding.layoutRefresh.isRefreshing = progress
//        })
    }

    companion object {
        private val TAG = HomeFragment1::class.java.simpleName

        fun newInstance() = HomeFragment1().apply {
            arguments = bundleOf()
        }
    }
}