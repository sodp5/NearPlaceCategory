package com.munny.nearplacecategory.ui.article

import com.munny.nearplacecategory.model.StoreImage
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleDataSource: ArticleDataSource
) : ArticleRepository {
    override suspend fun getImage(query: String): StoreImage {
        return articleDataSource.getImage(query)
    }

    override suspend fun getStaticMap(
        latitude: Double,
        longitude: Double,
        width: Int,
        height: Int
    ): Any {
        return articleDataSource.getStaticMap(latitude, longitude, width, height)
    }
}