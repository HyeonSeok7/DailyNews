package com.example.dailynews.feature.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailynews.MainActivity
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentHomeBinding
import com.example.dailynews.utils.Constants
import com.prof.rssparser.Parser
import java.nio.charset.Charset

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        bindUI()

        val parser = Parser.Builder()
            .context(mContext)
            .charset(Charset.forName("ISO-8859-7"))
            .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
            .build()


        return binding.root
    }

    override fun onRefresh() {
        binding.layoutRefresh.isRefreshing = false
    }

    private fun bindUI() {
        binding.layoutRefresh.setOnRefreshListener(this)
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }
    }

}