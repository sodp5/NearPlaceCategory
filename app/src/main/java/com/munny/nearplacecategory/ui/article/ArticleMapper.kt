package com.munny.nearplacecategory.ui.article

import com.munny.nearplacecategory.api.response.ImageSearchResponse
import com.munny.nearplacecategory.model.StoreImage

class ArticleMapper {
    fun imageSearchResponseToStoreImage(imageSearchResponse: ImageSearchResponse): StoreImage {
        val items = imageSearchResponse.items
        val firstItem = items.firstOrNull()

        return firstItem?.let {
            StoreImage(it.link, it.thumbnail)
        } ?: StoreImage.Empty
    }
}