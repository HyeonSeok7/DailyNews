package com.hy.dailynews

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hy.dailynews.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bindTitleImg")
fun bindViewTitleImg(view: ImageView, url: String?) {
    val requestOptions = RequestOptions().apply {
        placeholder(R.mipmap.image_news)
    }
    if (url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(R.mipmap.image_news)
            .apply(requestOptions)
            .thumbnail(0.1f)
            .into(view)
    } else {
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.1f)
            .apply(requestOptions)
            .into(view)
    }

}

@BindingAdapter("bindBannerImg")
fun bindViewBannerImg(view: ImageView, url: String?) {
    val requestOptions = RequestOptions().apply {
        isMemoryCacheable
        placeholder(R.mipmap.image_news)
//        centerCrop()
    }
    if (url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(R.mipmap.image_news)
            .apply(requestOptions)
            .thumbnail(0.1f)
            .into(view)
    } else {
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.1f)
            .apply(requestOptions)
            .into(view)
    }

}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("bindWebUrl")
fun bindViewWebViewUrl(view:WebView, url: String?) {
    if (url.isNullOrEmpty()) {
        Log.d("bindWebUrl", "is null url:$url")
    } else {
        view.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true // window.open()을 사용하기 위함
            allowFileAccessFromFileURLs = true
            saveFormData = false
            savePassword = false
            useWideViewPort = true // html 컨테츠가 웹뷰에 맞게
            setSupportMultipleWindows(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        }
        view.apply {
            /* 리다이렉트 할 때 브라우저 열리는 것 방지*/
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            /* 웹 뷰 띄우기 */
            url.let { loadUrl(it) }
        }
    }
}

// to Date
@SuppressLint("SetTextI18n")
@BindingAdapter("bindToDate")
fun bindViewToDate(view: TextView, value: String?) {
    if (value == null) view.text = ""
    else {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val date = format.parse(value)

        date?.apply {
            val time = Calendar.getInstance(Locale.KOREA).timeInMillis - this.time
            Log.d("bindToDate", "timeMillis => $time")
            when {
                time < Constants.Time.MINUTES -> view.text = "${time/Constants.Time.SECONDS}초 전"
                time < Constants.Time.HOURS   -> view.text = "${time/Constants.Time.MINUTES}분 전"
                time < Constants.Time.DAYS    -> view.text = "${time/Constants.Time.HOURS}시간 전"
                time < Constants.Time.WEEKS   -> view.text = "${time/Constants.Time.DAYS}일 전"
                time < Constants.Time.MONTHS  -> view.text = "${time/Constants.Time.WEEKS}주 전"
                time < Constants.Time.YEARS   -> view.text = "${time/Constants.Time.MONTHS}개월 전"
                else                          -> view.text = "${time/Constants.Time.YEARS}년 전"
            }
        }
    }
}