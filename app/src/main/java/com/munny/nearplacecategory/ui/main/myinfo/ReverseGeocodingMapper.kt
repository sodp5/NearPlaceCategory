package com.munny.nearplacecategory.ui.main.myinfo

import com.munny.nearplacecategory.api.response.ReverseGeocodingResponse

class ReverseGeocodingMapper {
    fun regionToName(region: ReverseGeocodingResponse.Region): String {
        return with(region) {
            "${area1.name} ${area2.name} ${area3.name} ${area4.name}"
        }
    }
}