package com.munny.nearplacecategory.ui.article

import com.munny.nearplacecategory.api.response.ImageSearchResponse
import com.munny.nearplacecategory.model.ArticleImage

class ArticleMapper {
    fun imageSearchResponseToArticleImage(imageSearchResponse: ImageSearchResponse): ArticleImage {
        val items = imageSearchResponse.items
        val firstItem = items.firstOrNull()

        return firstItem?.let {
            ArticleImage(it.link, it.thumbnail)
        } ?: ArticleImage.Empty
    }
}