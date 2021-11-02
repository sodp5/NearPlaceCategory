package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.model.ArticleImage
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

            categoryResponse.documents.map(placeMapper::documentToPlace)
                .also {
                    categoryList.addAll(it)
                }

            isEnd = categoryResponse.meta.is_end
        }

        return categoryList
    }

    override suspend fun getArticleImage(query: String): ArticleImage {
        return nearCategoryListDataSource.getArticleImage(query)
    }
}