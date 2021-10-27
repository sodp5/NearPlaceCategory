package com.munny.nearplacecategory.api

import com.munny.nearplacecategory._base.ApiAuth
import com.munny.nearplacecategory.api.response.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverSearchApi {
    @GET("v1/search/local.json")
    suspend fun getPoi(
        @Query("query") query: String,
        @Query("display") display: Int = 5
    ): Any

    @GET("v1/search/image")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("display") display: Int = 1,
        @Query("sort") sort: String = "sim"
    ): ImageSearchResponse

    class NaverSearchAuth : ApiAuth {
        override fun getApiUrl(): String {
            return "https://openapi.naver.com/"
        }

        override fun getHeaders(): Map<String, Any> {
            return mapOf(
                "X-Naver-Client-Id" to CLIENT_ID,
                "X-Naver-Client-Secret" to CLIENT_SECRET
            )
        }

        private companion object {
            const val CLIENT_ID = "qARRa6KY7uxFBhG905Sr"
            const val CLIENT_SECRET = "0fBpNmMXXH"
        }
    }
}