package com.munny.nearplacecategory.ui.shared.articleimage

import com.munny.nearplacecategory.model.ArticleImage
import javax.inject.Inject

class ArticleImageRepositoryImpl @Inject constructor(
    private val articleImageDataSource: ArticleImageDataSource
) : ArticleImageRepository {
    override suspend fun getArticleImage(query: String): ArticleImage {
        return articleImageDataSource.getArticleImage(query)
    }
}