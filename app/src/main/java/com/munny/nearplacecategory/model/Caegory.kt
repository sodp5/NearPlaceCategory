package com.munny.nearplacecategory.model

data class Category(
    val name: String,
    var category: Category? = null
)
