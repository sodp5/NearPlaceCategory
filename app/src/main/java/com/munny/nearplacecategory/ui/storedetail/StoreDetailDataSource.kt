package com.munny.nearplacecategory.ui.storedetail

import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.model.StoreImage
import javax.inject.Inject

class StoreDetailDataSource @Inject constructor(
    private val naverSearchApi: NaverSearchApi,
    private val naverCloudPlatformApi: NaverCloudPlatformApi
) {
    suspend fun getImage(query: String): StoreImage {
        val response = naverSearchApi.getImage(query)

        return StoreImageMapper().imageSearchResponseToStoreImage(response)
    }

    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Any {
        val lngLat = "$longitude,$latitude"

        return naverCloudPlatformApi.getStaticMap(lngLat, width, height)
    }
}