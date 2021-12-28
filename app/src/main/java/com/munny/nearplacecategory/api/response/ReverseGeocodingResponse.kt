package com.munny.nearplacecategory.api.response

data class ReverseGeocodingResponse(
    val results: List<Result>
) {
    data class Result(
        val region: Region
    )

    data class Region(
        val area1: Area,
        val area2: Area,
        val area3: Area,
        val area4: Area
    )

    data class Area(
        val name: String
    )
}
