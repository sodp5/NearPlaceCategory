package com.munny.nearplacecategory.ui.storedetail

import com.munny.nearplacecategory.api.response.ImageSearchResponse
import com.munny.nearplacecategory.model.StoreImage

class StoreImageMapper {
    fun imageSearchResponseToStoreImage(imageSearchResponse: ImageSearchResponse): StoreImage {
        val items = imageSearchResponse.items
        val firstItem = items.firstOrNull()

        return firstItem?.let {
            StoreImage(it.link, it.thumbnail)
        } ?: StoreImage.Empty
    }
}