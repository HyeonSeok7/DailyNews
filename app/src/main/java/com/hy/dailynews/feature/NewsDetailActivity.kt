package com.hy.dailynews.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hy.dailynews.R
import com.hy.dailynews.bindViewWebViewUrl
import com.hy.dailynews.databinding.ActivityNewsDetailBinding
import com.hy.dailynews.utils.Constants

class NewsDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHasExtra()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_close -> onBackPressed()
        }
    }

    private fun initHasExtra() {
        if (intent.hasExtra(Constants.ExtraKey.KEY_WEB_URL)) {
            intent.getSerializableExtra(Constants.ExtraKey.KEY_WEB_URL).let {
                Log.v(TAG,"webUrl:$it")
                bindViewWebViewUrl(binding.wvArticle, it.toString())
            }
        }
        if (intent.hasExtra(Constants.ExtraKey.KEY_SITE_NAME)) {
            intent.getSerializableExtra(Constants.ExtraKey.KEY_SITE_NAME).let {
                Log.v(TAG,"siteName:$it")
                binding.layoutAppbar.title = it.toString()
            }
        }
    }

    companion object {
        private val TAG = NewsDetailActivity::class.java.simpleName
    }
}