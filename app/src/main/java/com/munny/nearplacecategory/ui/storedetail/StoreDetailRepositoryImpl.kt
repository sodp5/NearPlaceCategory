package com.munny.nearplacecategory.ui.storedetail

import com.munny.nearplacecategory.model.StoreImage
import javax.inject.Inject

class StoreDetailRepositoryImpl @Inject constructor(
    private val storeDetailDataSource: StoreDetailDataSource
) : StoreDetailRepository {
    override suspend fun getImage(query: String): StoreImage {
        return storeDetailDataSource.getImage(query)
    }

    override suspend fun getStaticMap(
        latitude: Double,
        longitude: Double,
        width: Int,
        height: Int
    ): Any {
        return storeDetailDataSource.getStaticMap(latitude, longitude, width, height)
    }
}