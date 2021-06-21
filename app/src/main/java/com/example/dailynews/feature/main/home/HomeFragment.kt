package com.example.dailynews.feature.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentHomeBinding
import com.example.dailynews.utils.Constants
import com.prof.rssparser.Parser
import java.nio.charset.Charset

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mContext: Context
    private lateinit var mAdapter: HomeRVAdapter
    private lateinit var parser: Parser
    private val viewModel: HomeViewModel by viewModels()
    private val linearLayoutManager = LinearLayoutManager(context)

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        mContext = context
    }

    override fun onRefresh() {
        Log.d(TAG,"onRefresh")
        binding.layoutRefresh.isRefreshing = false
        mAdapter.clear()
        loadParserData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG,"onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        Log.d(TAG,"initView")
        binding.layoutRefresh.setOnRefreshListener(this)
            parser = Parser.Builder()
                .context(mContext)
                .charset(Charset.forName("ISO-8859-7"))
                .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
                .build()
            initRVLayout()
            loadParserData()
    }

    private fun initRVLayout() {
        Log.v(TAG,"initRVLayout")
        binding.rvNewsList.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            mAdapter = HomeRVAdapter()
            adapter = mAdapter
        }

            viewModel.rssChannel.observe(viewLifecycleOwner, { channel ->
                if (channel != null) {
                    Log.i(TAG,"rssChannel observe In, channel:$channel\n channel.articles:${channel.articles}")
                    mAdapter.addItems(channel.articles)
                    binding.rvNewsList.adapter = mAdapter
                }
            })

    }

    private fun loadParserData() {
        Log.d(TAG,"loadParserData")
        viewModel.fetchForUrlAndParseRawData(Constants.GoogleRSS.BASE_URL)
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }
    }

}