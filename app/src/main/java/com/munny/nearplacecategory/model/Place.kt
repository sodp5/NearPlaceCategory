package com.munny.nearplacecategory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: Long,
    val name: String,
    val articleImage: ArticleImage?,
    val categories: List<String>,
    val phone: String,
    val distance: Int,
    val longitude: Double,
    val latitude: Double
): Parcelable
