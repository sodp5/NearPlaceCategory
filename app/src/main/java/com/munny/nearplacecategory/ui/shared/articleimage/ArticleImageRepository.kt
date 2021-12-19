package com.munny.nearplacecategory.ui.shared.articleimage

import com.munny.nearplacecategory.model.ArticleImage

interface ArticleImageRepository {
    suspend fun getArticleImage(query: String): ArticleImage
}