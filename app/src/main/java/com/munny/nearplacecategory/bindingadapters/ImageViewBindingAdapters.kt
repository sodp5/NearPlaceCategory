package com.munny.nearplacecategory.bindingadapters

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.extensions.ifFalse
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.model.ArticleImage

@BindingAdapter("setArticleThumbnailImage")
fun setArticleThumbnailImage(iv: ImageView, articleImage: ArticleImage?) {
    articleImage?.url?.let {
        val roundedOption = RequestOptions()
        roundedOption.transform(CenterCrop(), RoundedCorners(32))

        it.isNotEmpty().ifTrue {
            Glide.with(iv.context)
                .load(it)
                .thumbnail(0.2f)
                .apply(roundedOption)
                .into(iv)
        }.ifFalse {
            Glide.with(iv.context)
                .load(R.drawable.ic_restaurant_placeholder)
                .apply(roundedOption)
                .into(iv)
        }
    }
}

@BindingAdapter("setArticleImage")
fun setArticleImage(iv: ImageView, articleImage: ArticleImage?) {
    articleImage?.url?.let {
        it.isNotEmpty().ifTrue {
            Glide.with(iv.context)
                .load(it)
                .thumbnail(0.2f)
                .centerCrop()
                .into(iv)
        }.ifFalse {
            iv.visibility = View.GONE
        }
    }
}

@BindingAdapter("setBitmap")
fun setBitmap(iv: ImageView, bitmap: Bitmap?) {
    bitmap?.let {
        iv.setImageBitmap(it)
    }
}