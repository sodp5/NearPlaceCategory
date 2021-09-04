package com.munny.nearplacecategory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(
    val categoryName: String,
    val placeList: List<Place>
): Parcelable
