package com.munny.nearplacecategory.ui.storedetail

import com.munny.nearplacecategory.api.response.ImageSearchResponse
import com.munny.nearplacecategory.model.StoreImage

class StoreImageMapper {
    fun imageSearchResponseToStoreImage(imageSearchResponse: ImageSearchResponse): StoreImage {
        return imageSearchResponse.items.first().let {
            StoreImage(it.link, it.thumbnail)
        }
    }
}