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
        var size = 15

        while (!isEnd) {
            val response = nearCategoryListDataSource.getPlaceByCategory(
                categoryCode,
                latitude,
                longitude,
                page++,
                500,
                size
            )

            response.documents.map { document ->
                val splits = document.category_name.split(">").map { it.trim() }

                val category = Category(splits[0])
                var currCategory = category

                splits.filterIndexed { index, _ ->  index != 0}
                    .forEach {
                        currCategory.category = Category(it)
                        currCategory = currCategory.category ?: return@forEach
                    }

                Place(
                    id = document.id,
                    placeName = document.place_name,
                    category = category,
                    phone = document.phone,
                    distance = document.distance,
                    longitude = longitude,
                    latitude = latitude
                )
            }.let { categoryList.addAll(it) }

            isEnd = categoryList.size >= response.meta.total_count

            val gap = response.meta.total_count - categoryList.size
            size = if (gap / 15 >= 1) {
                15
            } else {
                gap
            }
        }

        return categoryList
    }
}