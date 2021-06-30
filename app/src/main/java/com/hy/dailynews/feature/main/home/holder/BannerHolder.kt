package com.hy.dailynews.feature.main.home.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.hy.dailynews.databinding.ItemBannerBinding
import com.hy.dailynews.feature.main.home.adapter.HomeBestNewsSliderAdapter
import com.hy.dailynews.models.HomeModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlin.random.Random

class BannerHolder(var binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {

    private val mSliderAdapter = HomeBestNewsSliderAdapter(binding.root.context)

    fun bind(item: List<HomeModel>) {
        item.let {
            binding.sliderView.apply {
                setSliderAdapter(mSliderAdapter)
                setIndicatorAnimation(IndicatorAnimationType.SWAP)
                setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                setSliderAnimationDuration(600)
                setIndicatorAnimationDuration(600)
                scrollTimeInSec = 3
                startAutoCycle()
                Log.e(TAG,"ItemSize:${it.size}")
                mSliderAdapter.clear()
                if (it.isNotEmpty()) {
                    val size = it.size
                    val ran = Random
                    val num = hashSetOf<Int>()
                    while (num.size != 5) {
                        num.add(ran.nextInt(size))
                    }
                    num.map { index ->
                        mSliderAdapter.addItem(it[index].newsList)
                    }
                }

            }
        }
    }


    companion object {
        private val TAG = BannerHolder::class.java.simpleName
    }

}