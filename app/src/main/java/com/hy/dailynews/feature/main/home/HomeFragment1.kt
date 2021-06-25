package com.hy.dailynews.feature.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hy.dailynews.databinding.FragmentHome1Binding


class HomeFragment1 : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHome1Binding
    private lateinit var mAdapter: HomeListAdapter
    private lateinit var mContext: Context
    private lateinit var homeViewModel: HomeNewsListViewModel

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
        mContext = context
    }

    override fun onRefresh() {
        Log.d(TAG, "onRefresh")
        mAdapter.clear()
        homeViewModel.updateNewsData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome1Binding.inflate(inflater).apply {
            val homeListViewModelFactory = HomeNewsListViewModelFactory(HomeRepository(RemoteNewsData()))
            homeViewModel = ViewModelProvider(requireActivity(), homeListViewModelFactory).get(HomeNewsListViewModel::class.java)
            viewModel = homeViewModel
        }
        binding.layoutRefresh.setOnRefreshListener(this)
        initRVLayout()
        setUpData()
        return binding.root
    }

    private fun initRVLayout() {
        Log.d(TAG, "initRVLayout")
        mAdapter = HomeListAdapter(mContext)
            .apply {
                setHasStableIds(true)
            }
        binding.rvNewsList.apply {
            this.adapter = mAdapter
        }
    }

    private fun setUpData() {
        Log.v(TAG,":setUpData")
        val newList = mutableListOf<String>()

        // 뉴스 세팅
        homeViewModel.newList.observe(viewLifecycleOwner, Observer {
            mAdapter.addItems(it)
        })
//        homeViewModel.progress.observe(viewLifecycleOwner, Observer {
//            binding.layoutRefresh.isRefreshing = it
//        })

    }

    companion object {
        private val TAG = HomeFragment1::class.java.simpleName


        fun newInstance() = HomeFragment1().apply {
            arguments = bundleOf()
        }

    }
}
