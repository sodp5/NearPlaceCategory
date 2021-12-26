package com.munny.nearplacecategory.ui.shared.favorite

import com.munny.nearplacecategory.model.Place

interface FavoriteRepository {
    suspend fun insertPlace(place: Place)

    suspend fun deletePlace(id: Long)

    suspend fun getAllPlace(): List<Place>

    suspend fun getAllPlaceId(): List<Long>
}