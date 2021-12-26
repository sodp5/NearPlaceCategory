package com.munny.nearplacecategory.usecase

import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import javax.inject.Inject

class SwitchFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun switchFavorite(place: Place): Place {
        val liked = place.isLiked.not()

        val switchedPlace = place.copy(
            isLiked = liked
        )

        if (liked) {
            addFavorite(switchedPlace)
        } else {
            removeFavorite(switchedPlace)
        }

        return switchedPlace
    }

    private suspend fun addFavorite(place: Place) {
        favoriteRepository.insertPlace(place)
    }

    private suspend fun removeFavorite(place: Place) {
        favoriteRepository.deletePlace(place.id)
    }
}