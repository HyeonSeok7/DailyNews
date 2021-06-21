package com.example.dailynews

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.prof.rssparser.Article


@BindingAdapter("bindLoadingGif")
fun bindViewLoadingGif(view: ImageView, data: String) {
    // 임시 명칭 dat
    // 추후에는 모델로 view 컨트롤
    Glide.with(view.context)
        .asGif()
        .load(R.mipmap.loading_gif)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Glide에서 캐싱한 리소스와 로드할 리소스가 같을 때 캐싱된 리소스 사용
        .into(view)
}

@BindingAdapter("bindTitleImg")
fun bindViewTitleImg(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(R.mipmap.image_news)
            .into(view)
    } else Log.d("bindTitleImg", "=> $url")

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
