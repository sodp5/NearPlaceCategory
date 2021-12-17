package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory.api.response.PlaceByCategoryResponse
import com.munny.nearplacecategory.model.Place

class PlaceMapper {
    fun documentToPlace(document: PlaceByCategoryResponse.Document): Place {
        val categories = document.category_name.split(">").map { it.trim() }

        return Place(
            id = document.id,
            name = document.place_name,
            articleImage = null,
            categories = categories,
            phone = document.phone,
            distance = document.distance.ifEmpty { "0" }.toInt(),
            longitude = document.longitude,
            latitude = document.latitude
        )
    }
}