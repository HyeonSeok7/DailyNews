package com.hy.dailynews.feature.main.home.holder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemBannerBinding
import com.hy.dailynews.feature.NewsDetailActivity
import com.hy.dailynews.feature.main.home.adapter.HomeBestNewsSliderAdapter
import com.hy.dailynews.models.News
import com.hy.dailynews.utils.Constants
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class BannerHolder(var binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {

    private val mSliderAdapter = HomeBestNewsSliderAdapter(binding.root.context)

    fun bind(items: MutableList<News>) {
        binding.sliderView.apply {
            setSliderAdapter(mSliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.SWAP)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            setSliderAnimationDuration(600)
            setIndicatorAnimationDuration(600)
            scrollTimeInSec = 3
            startAutoCycle()
            mSliderAdapter.clear()
            mSliderAdapter.addItems(items)
        }
    }

    companion object {
        private val TAG = BannerHolder::class.java.simpleName
    }

}