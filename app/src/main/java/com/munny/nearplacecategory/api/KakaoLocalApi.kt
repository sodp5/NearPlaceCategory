package com.munny.nearplacecategory.api

import com.munny.nearplacecategory._base.ApiAuth
import com.munny.nearplacecategory.api.response.PlaceByCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoLocalApi {
    @GET("/v2/local/search/category.json")
    suspend fun getPlaceByCategory(
        @Query("category_group_code") categoryGroupCode: String,
        @Query("y") latitude: String,
        @Query("x") longitude: String,
        @Query("page") page: Int,
        @Query("radius") radius: Int,
        @Query("size") size: Int
    ): PlaceByCategoryResponse

    class KakaoLocalAuth : ApiAuth {
        override fun getApiUrl(): String {
            return "https://dapi.kakao.com/"
        }

        override fun getHeaders(): Map<String, Any> {
            return mapOf("Authorization" to "KakaoAK $REST_API_KEY")
        }

        private companion object {
            const val REST_API_KEY = "1e166c7187543abe85e66a34e3a3a336"
        }
    }
}