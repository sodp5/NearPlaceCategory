package com.munny.nearplacecategory.ui.main.myinfo

import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import javax.inject.Inject

class MyInfoDataSource @Inject constructor(
    private val naverCloudPlatformApi: NaverCloudPlatformApi
) {
    private val reverseGeocodingMapper = ReverseGeocodingMapper()

    suspend fun getReverseGeocoding(longitude: Double, latitude: Double): String {
        val lonLat = "$longitude,$latitude"

        return naverCloudPlatformApi.getReverseGeocoding(lonLat)
            .results
            .getOrNull(0)
            ?.region
            ?.let(reverseGeocodingMapper::regionToName)
            ?: "-"
    }
}