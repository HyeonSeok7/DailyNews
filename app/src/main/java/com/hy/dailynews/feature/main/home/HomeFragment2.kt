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
import com.hy.dailynews.databinding.FragmentHome2Binding
import com.hy.dailynews.feature.main.home.adapter.HomeNewsListAdapter
import com.hy.dailynews.feature.main.home.repositories.HomeRepository
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModel
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModelFactory
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.utils.Constants

class HomeFragment2 : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHome2Binding
    private lateinit var mAdapter: HomeNewsListAdapter
    private lateinit var homeNewsListViewModel: HomeNewsListViewModel

    override fun onRefresh() {
        mAdapter.bannerClear()
        mAdapter.listClear()
        homeNewsListViewModel.updateBestNewsData()
        homeNewsListViewModel.updateNewsData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome2Binding.inflate(inflater).apply {
            val homeListViewModelFactory = HomeNewsListViewModelFactory(HomeRepository(RemoteNewsData()))
            homeNewsListViewModel = ViewModelProvider(requireActivity(), homeListViewModelFactory).get(HomeNewsListViewModel::class.java)
        }
        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        initData()

        return binding.root
    }

    private fun initRVLayout() {
        Log.d(TAG, "initRVLayout")

        mAdapter = HomeNewsListAdapter().apply {
            setHasStableIds(true)
        }

        binding.rvNewsList.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initData() {
        Log.d(TAG, "initData")

        // 뉴스 셋팅
        homeNewsListViewModel.bestNewsList.observe(viewLifecycleOwner, { data ->
            mAdapter.bannerClear()
            data.forEach { bannerItem ->
                mAdapter.bannerModel(HomeModel(Constants.ViewType.BANNER_TYPE, bannerItem))
            }

        })

        homeNewsListViewModel.newsList.observe(viewLifecycleOwner, { data ->
            mAdapter.listClear()
            mAdapter.addModel(HomeModel(Constants.ViewType.BANNER_TYPE, null))
            data.forEach { listItem ->
                mAdapter.addModel(HomeModel(Constants.ViewType.NEWS_LIST_TYPE, listItem))
            }
        })

        homeNewsListViewModel.progress.observe(viewLifecycleOwner, { bar ->
            binding.layoutRefresh.isRefreshing = bar
        })
    }

    companion object {
        private val TAG = HomeFragment2::class.java.simpleName

        fun newInstance() = HomeFragment2().apply {
            arguments = bundleOf()
        }
    }
}