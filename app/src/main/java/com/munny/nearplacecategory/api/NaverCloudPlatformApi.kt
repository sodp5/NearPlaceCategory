package com.munny.nearplacecategory.api

import com.munny.nearplacecategory._base.ApiAuth
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverCloudPlatformApi {
    @GET("map-reversegeocode/v2/gc")
    suspend fun getReverseGeocoding(
        @Query("coords") coords: String,
        @Query("output") output: String = "json"
    ): Any

    @GET("map-geocode/v2/geocode")
    suspend fun getGeocoding(
        @Query("query") coords: String,
        @Query("coordinate") coordinate: String
    ): Any

    @GET("map-static/v2/raster")
    suspend fun getStaticMap(
        @Query("center") lngLat: String,
        @Query("width") width: Int,
        @Query("height") height: Int,
        @Query("level") level: Int = 5
    ) : Any

    class NaverCloudPlatformAuth : ApiAuth {
        override fun getApiUrl(): String {
            return "https://naveropenapi.apigw.ntruss.com/"
        }

        override fun getHeaders(): Map<String, Any> {
            return mapOf(
                "X-NCP-APIGW-API-KEY-ID" to CLIENT_ID,
                "X-NCP-APIGW-API-KEY" to CLIENT_SECRET
            )
        }

        private companion object {
            const val CLIENT_ID = "emzppx9dn2"
            const val CLIENT_SECRET = "elfloCXeNXYxethM8sCTe2JkwhbgUgNsMFwiv2rg"
        }
    }
}