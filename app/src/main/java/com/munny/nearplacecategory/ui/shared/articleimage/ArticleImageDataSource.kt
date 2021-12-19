package com.munny.nearplacecategory.ui.shared.articleimage

import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.model.ArticleImage
import com.munny.nearplacecategory.ui.article.ArticleMapper
import javax.inject.Inject

class ArticleImageDataSource @Inject constructor(
    private val naverSearchApi: NaverSearchApi
) {
    suspend fun getArticleImage(query: String): ArticleImage {
        val response = naverSearchApi.getImage(query)

        return ArticleMapper().imageSearchResponseToArticleImage(response)
    }
}