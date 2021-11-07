package com.munny.nearplacecategory.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object GlideUtil {
    fun loadBitmap(
        context: Context,
        url: String?,
        onSuccessAction: ((bitmap: Bitmap?) -> Unit)? = null,
        onFailureAction: (() -> Unit)? = null
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onFailureAction?.invoke()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccessAction?.invoke(resource)

                    return false
                }

            })
            .submit()

    }
}