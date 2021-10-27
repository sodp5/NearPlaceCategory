package com.munny.nearplacecategory.api.response

data class ImageSearchResponse(
    val items: List<Item>
) {
    data class Item(
        val link: String,
        val thumbnail: String
    )
}
