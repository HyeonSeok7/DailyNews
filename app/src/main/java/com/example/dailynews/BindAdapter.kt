package com.example.dailynews

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
fun bindViewTitleImg(view: ImageView, data: Article) {
    Glide.with(view.context)
        .load(data.image)
        .into(view)
}