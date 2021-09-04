package com.munny.nearplacecategory.model

data class Place(
    val id: Long,
    val placeName: String,
    val category: Category?,
    val phone: String,
    val distance: String,
    val longitude: Double,
    val latitude: Double
)
