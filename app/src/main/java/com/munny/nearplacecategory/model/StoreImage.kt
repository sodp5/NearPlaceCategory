package com.munny.nearplacecategory.model

data class StoreImage(
    val url: String,
    val thumbnailUrl: String
) {
    companion object {
        val Empty get() = StoreImage("", "")
    }
}
