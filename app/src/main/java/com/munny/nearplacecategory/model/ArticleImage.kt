package com.munny.nearplacecategory.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleImage(
    val url: String,
    val thumbnailUrl: String
) : Parcelable {
    companion object {
        val Empty get() = ArticleImage("", "")
    }
}
