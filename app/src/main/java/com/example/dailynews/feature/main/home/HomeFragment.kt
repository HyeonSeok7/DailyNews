package com.example.dailynews.feature.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        bindUI()
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