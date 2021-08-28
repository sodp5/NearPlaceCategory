package com.munny.nearplacecategory.api

import retrofit2.http.GET
import retrofit2.http.Query

interface PoiSearchApi {
    @GET("v1/search/local.json")
    suspend fun getPoi(@Query("query") query: String): Any

    companion object {
        const val URL = "https://openapi.naver.com/"
    }
}