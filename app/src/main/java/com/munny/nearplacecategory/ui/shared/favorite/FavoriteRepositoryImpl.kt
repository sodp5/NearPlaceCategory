package com.munny.nearplacecategory.ui.shared.favorite

import com.munny.nearplacecategory.model.Place
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource
) : FavoriteRepository {
    override suspend fun insertPlace(place: Place) {
        favoriteDataSource.insertPlace(place)
    }

    override suspend fun deletePlace(id: Long) {
        favoriteDataSource.deletePlace(id)
    }

    override suspend fun getAllPlace(): List<Place> {
        return favoriteDataSource.getAllPlace()
    }
}