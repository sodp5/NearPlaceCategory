package com.munny.nearplacecategory.ui.shared.favorite

import com.munny.nearplacecategory.db.PlaceDao
import com.munny.nearplacecategory.model.Place
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    private val placeDao: PlaceDao
) {
    private val placeEntityMapper = PlaceEntityMapper()

    suspend fun insertPlace(place: Place) {
        val entity = placeEntityMapper.toPlaceEntity(place)

        placeDao.insertPlace(entity)
    }

    suspend fun deletePlace(id: Long) {
        placeDao.deletePlace(id)
    }

    suspend fun getAllPlace(): List<Place> {
        return placeDao.getAllPlace()
            .sortedBy {
                -it.mills
            }
            .map(
                placeEntityMapper::toPlace
            )
    }
}