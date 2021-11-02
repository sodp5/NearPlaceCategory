package com.munny.nearplacecategory.ui.nearcategorylist

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
        val placeMapper = PlaceMapper()
        val categoryList = mutableListOf<Place>()
        var isEnd = false
        var page = 1
        val size = 15

        while (!isEnd) {
            val categoryResponse = nearCategoryListDataSource.getPlaceByCategory(
                categoryCode,
                latitude,
                longitude,
                page++,
                500,
                size
            )

            categoryResponse.documents.map{ document ->
                val articleImage = nearCategoryListDataSource.getArticleImage(document.place_name)

                placeMapper.documentToPlace(document, articleImage)
            }.let {
                categoryList.addAll(it)
            }

            isEnd = categoryResponse.meta.is_end
        }

        return categoryList
    }
}