package com.munny.nearplacecategory.ui.shared.articleimage


interface ArticleImageRepository {
    suspend fun getArticleImageUrl(query: String): String
}