package com.example.dailynews.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.dailynews.R
import com.example.dailynews.bindViewWebViewUrl
import com.example.dailynews.databinding.ActivityArticleDetailBinding
import com.example.dailynews.utils.Constants

class ArticleDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityArticleDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
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
        if (intent.hasExtra(Constants.ExtraKey.KEY_SOURCE_NAME)) {
            intent.getSerializableExtra(Constants.ExtraKey.KEY_SOURCE_NAME).let {
                Log.v(TAG,"sourceName:$it")
                binding.layoutAppbar.title = it.toString()
            }
        }
    }

    companion object {
        private val TAG = ArticleDetailActivity::class.java.simpleName
    }
}