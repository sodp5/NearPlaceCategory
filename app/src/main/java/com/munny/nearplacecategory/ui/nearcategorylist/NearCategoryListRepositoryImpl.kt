package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.model.Category
import com.munny.nearplacecategory.model.Place
import javax.inject.Inject

class NearCategoryListRepositoryImpl @Inject constructor(
    private val nearCategoryListDataSource: NearCategoryListDataSource
) : NearCategoryListRepository {

    override suspend fun getPlaceByCategory(
        categoryCode: String,
        latitude: Double,
        longitude: Double
    ): List<Place> {
        val categoryList = mutableListOf<Place>()
        var isEnd = false
        var page = 1
        val size = 15

        while (!isEnd) {
            val response = nearCategoryListDataSource.getPlaceByCategory(
                categoryCode,
                latitude,
                longitude,
                page++,
                500,
                size
            )

            response.documents.map(PlaceMapper()::documentToPlace)
                .let { categoryList.addAll(it) }

            isEnd = response.meta.is_end
        }

        return categoryList
    }
}