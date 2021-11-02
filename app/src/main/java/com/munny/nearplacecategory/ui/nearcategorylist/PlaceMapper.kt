package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.api.response.PlaceByCategoryResponse
import com.munny.nearplacecategory.model.ArticleImage
import com.munny.nearplacecategory.model.Category
import com.munny.nearplacecategory.model.Place

class PlaceMapper {
    fun documentToPlace(
        document: PlaceByCategoryResponse.Document,
        articleImage: ArticleImage
    ): Place {
        val splits = document.category_name.split(">").map { it.trim() }

        val category = Category(splits[0])
        var currCategory: Category? = category

        splits.filterIndexed { index, _ ->  index != 0}
            .forEach {
                currCategory?.category = Category(it)
                currCategory = currCategory?.category
            }

        return Place(
            id = document.id,
            name = document.place_name,
            articleImage = articleImage,
            category = category,
            phone = document.phone,
            distance = document.distance.toInt(),
            longitude = document.longitude,
            latitude = document.latitude
        )
    }
}