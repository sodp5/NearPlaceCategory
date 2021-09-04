package com.munny.nearplacecategory.api.response

import com.google.gson.annotations.SerializedName

data class PlaceByCategoryResponse(
    val documents: List<Document>,
    val meta: Meta
) {
    data class Document(
        val id: Long,
        val place_name: String,
        val category_name: String,
        val phone: String,
        val distance: String,
        @SerializedName("x") val longitude: Double,
        @SerializedName("y") val latitude: Double
    )

    data class Meta(
        val is_end: Boolean,
        val total_count: Int
    )
}