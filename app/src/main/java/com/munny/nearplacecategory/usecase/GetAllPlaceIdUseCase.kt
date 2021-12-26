package com.munny.nearplacecategory.usecase

import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import javax.inject.Inject

class GetAllPlaceIdUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun getAllPlaceId(): List<Long> {
        return favoriteRepository.getAllPlaceId()
    }
}