package com.munny.nearplacecategory.ui.shared.articleimage

import com.munny.nearplacecategory.api.response.ImageSearchResponse

class ArticleImageMapper {
    fun imageSearchResponseToArticleImage(imageSearchResponse: ImageSearchResponse): String {
        val items = imageSearchResponse.items
        val firstItem = items.firstOrNull()

        return firstItem?.link ?: ""
    }
}