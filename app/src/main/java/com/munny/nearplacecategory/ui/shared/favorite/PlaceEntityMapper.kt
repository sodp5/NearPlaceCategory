package com.munny.nearplacecategory.ui.shared.favorite

import com.munny.nearplacecategory.db.entity.PlaceEntity
import com.munny.nearplacecategory.model.Place

class PlaceEntityMapper {
    fun toPlaceEntity(place: Place): PlaceEntity {
        return PlaceEntity(
            place.id,
            place
        )
    }

    fun toPlace(placeEntity: PlaceEntity): Place {
        return placeEntity.place
    }
}