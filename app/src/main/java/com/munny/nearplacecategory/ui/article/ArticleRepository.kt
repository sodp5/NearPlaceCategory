package com.munny.nearplacecategory.ui.article

import com.munny.nearplacecategory.model.StoreImage

interface ArticleRepository {
    suspend fun getImage(query: String) : StoreImage
    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Any
}