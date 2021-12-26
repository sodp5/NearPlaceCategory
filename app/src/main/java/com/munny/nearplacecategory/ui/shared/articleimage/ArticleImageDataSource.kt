package com.munny.nearplacecategory.ui.shared.articleimage

import com.munny.nearplacecategory.api.NaverSearchApi
import javax.inject.Inject

class ArticleImageDataSource @Inject constructor(
    private val naverSearchApi: NaverSearchApi
) {
    suspend fun getArticleImage(query: String): String {
        val response = naverSearchApi.getImage(query)

        return ArticleImageMapper().imageSearchResponseToArticleImage(response)
    }
}