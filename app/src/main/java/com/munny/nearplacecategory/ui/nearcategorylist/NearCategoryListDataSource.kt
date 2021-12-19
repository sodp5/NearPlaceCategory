package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.api.KakaoLocalApi
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.api.response.PlaceByCategoryResponse
import com.munny.nearplacecategory.model.ArticleImage
import com.munny.nearplacecategory.ui.article.ArticleMapper
import javax.inject.Inject

class NearCategoryListDataSource @Inject constructor(
    private val kakaoLocalApi: KakaoLocalApi
) {
    suspend fun getPlaceByCategory(
        categoryCode: String,
        latitude: Double,
        longitude: Double,
        page: Int,
        radius: Int,
        size: Int
    ): PlaceByCategoryResponse {
        return kakaoLocalApi.getPlaceByCategory(
            categoryCode,
            latitude.toString(),
            longitude.toString(),
            page,
            radius,
            size
        )
    }
}