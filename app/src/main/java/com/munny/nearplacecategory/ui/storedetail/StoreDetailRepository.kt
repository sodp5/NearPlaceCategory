package com.munny.nearplacecategory.ui.storedetail

import com.munny.nearplacecategory.model.StoreImage

interface StoreDetailRepository {
    suspend fun getImage(query: String) : StoreImage
    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Any
}