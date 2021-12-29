package com.munny.nearplacecategory.api

import com.munny.nearplacecategory._base.ApiAuth
import com.munny.nearplacecategory.api.response.ReverseGeocodingResponse
import com.munny.nearplacecategory.api.response.StaticMapVersionResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverCloudPlatformApi {
    @GET("map-reversegeocode/v2/gc")
    suspend fun getReverseGeocoding(
        @Query("coords") coords: String,
        @Query("output") output: String = "json"
    ): ReverseGeocodingResponse

    @GET("map-geocode/v2/geocode")
    suspend fun getGeocoding(
        @Query("query") coords: String,
        @Query("coordinate") coordinate: String
    ): Any

    @Headers("Content-Type: image/jpeg")
    @GET("map-static/v2/raster")
    suspend fun getStaticMap(
        @Query("center") center: String,
        @Query("markers") marker: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("dataversion") dataVersion: String,
        @Query("level") level: Int = 18
    ): ResponseBody

    @GET("map-static/lastversion")
    suspend fun getLastVersion(): StaticMapVersionResponse

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