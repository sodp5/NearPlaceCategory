package com.munny.nearplacecategory.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.munny.nearplacecategory.model.StoreImage

@BindingAdapter("setImageUrl")
fun setImageUrl(iv: ImageView, url: StoreImage?) {
    url?.let {
        Glide.with(iv.context)
            .load(it.url)
            .thumbnail(0.2f)
            .into(iv)
    }
}