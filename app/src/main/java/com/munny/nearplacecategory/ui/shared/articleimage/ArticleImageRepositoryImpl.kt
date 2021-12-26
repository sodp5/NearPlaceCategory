package com.munny.nearplacecategory.ui.shared.articleimage

import javax.inject.Inject

class ArticleImageRepositoryImpl @Inject constructor(
    private val articleImageDataSource: ArticleImageDataSource
) : ArticleImageRepository {
    override suspend fun getArticleImageUrl(query: String): String {
        return articleImageDataSource.getArticleImage(query)
    }
}