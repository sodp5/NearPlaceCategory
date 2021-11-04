package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import com.munny.nearplacecategory.model.ArticleImage
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleDataSource: ArticleDataSource
) : ArticleRepository {
    override suspend fun getImage(query: String): ArticleImage {
        return articleDataSource.getImage(query)
    }

    override suspend fun getStaticMap(
        latitude: Double,
        longitude: Double,
        width: Int,
        height: Int
    ): Bitmap {
        return articleDataSource.getStaticMap(latitude, longitude, width, height)
    }
}