package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Bitmap {
        val lngLat = "$longitude,$latitude"
        val marker = "type:d|size:mid|pos:$longitude $latitude"

//        val version = naverCloudPlatformApi.getLastVersion().version
        val version = "1.1"
        val body = naverCloudPlatformApi.getStaticMap(lngLat, marker, width, height, version)

        return BitmapFactory.decodeStream(body.byteStream())
    }
}