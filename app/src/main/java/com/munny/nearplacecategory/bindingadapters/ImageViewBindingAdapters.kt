package com.munny.nearplacecategory.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.munny.nearplacecategory.extensions.ifFalse
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.model.StoreImage

@BindingAdapter("setImageUrl")
fun setImageUrl(iv: ImageView, storeImage: StoreImage?) {
    storeImage?.url?.let {
        it.isNotEmpty().ifTrue {
            Glide.with(iv.context)
                .load(it)
                .thumbnail(0.2f)
                .into(iv)
        }.ifFalse {
            iv.visibility = View.GONE
        }
    }
}