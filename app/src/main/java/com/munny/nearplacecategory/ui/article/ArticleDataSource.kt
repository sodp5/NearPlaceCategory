package com.munny.nearplacecategory.ui.article

import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.model.ArticleImage
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    private val naverSearchApi: NaverSearchApi,
    private val naverCloudPlatformApi: NaverCloudPlatformApi
) {
    suspend fun getImage(query: String): ArticleImage {
        val response = naverSearchApi.getImage(query)

        return ArticleMapper().imageSearchResponseToArticleImage(response)
    }

    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Any {
        val lngLat = "$longitude,$latitude"

        return naverCloudPlatformApi.getStaticMap(lngLat, width, height)
    }
}