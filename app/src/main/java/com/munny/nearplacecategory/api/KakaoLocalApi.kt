package com.munny.nearplacecategory.api

import com.munny.nearplacecategory.base.ApiAuth
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoLocalApi {
    @GET("/v2/local/search/category.json")
    suspend fun getPlaceByCategory(
        @Query("category_group_code") categoryGroupCode: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("size") size: Int = 2
    ): Any

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