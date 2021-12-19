package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.model.Place

interface NearCategoryListRepository {
    suspend fun getPlaceByCategory(
        categoryCode: String,
        latitude: Double,
        longitude: Double
    ): List<Place>
}