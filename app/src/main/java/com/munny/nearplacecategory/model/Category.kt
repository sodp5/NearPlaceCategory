package com.munny.nearplacecategory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    var category: Category? = null
): Parcelable
