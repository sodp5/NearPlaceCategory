package com.munny.nearplacecategory.model

data class Place(
    val id: Long,
    val placeName: String,
    val category: List<String>,
    val phone: String,
    val distance: String,
    val longitude: Double,
    val latitude: Double
)
