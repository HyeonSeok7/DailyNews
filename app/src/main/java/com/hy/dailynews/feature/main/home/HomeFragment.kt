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
import com.hy.dailynews.databinding.FragmentHomeBinding
import com.hy.dailynews.feature.main.home.adapter.HomeNewsListAdapter
import com.hy.dailynews.feature.main.home.repositories.HomeRepository
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModel
import com.hy.dailynews.feature.main.home.viewModels.HomeNewsListViewModelFactory
import com.hy.dailynews.models.HomeModel
import com.hy.dailynews.utils.Constants

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
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
        binding = FragmentHomeBinding.inflate(inflater).apply {
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
            mAdapter.addModel(HomeModel(Constants.ViewType.BANNER_TYPE, null))
            data.forEach { bannerItem ->
                mAdapter.bannerModel(HomeModel(null, bannerItem))
            }

        })

        homeNewsListViewModel.newsList.observe(viewLifecycleOwner, { data ->
            mAdapter.listClear()
            data.forEach { listItem ->
                mAdapter.addModel(HomeModel(Constants.ViewType.NEWS_LIST_TYPE, listItem))
            }
        })

//        homeNewsListViewModel.progress.observe(viewLifecycleOwner, { bar ->
//            binding.layoutRefresh.isRefreshing = bar
//        })
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }
    }
}