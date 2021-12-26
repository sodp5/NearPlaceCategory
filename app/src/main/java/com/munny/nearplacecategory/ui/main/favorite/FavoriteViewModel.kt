package com.munny.nearplacecategory.ui.main.favorite

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import com.munny.nearplacecategory.usecase.GetAllPlaceIdUseCase
import com.munny.nearplacecategory.usecase.SwitchFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {
    val favoritePlaces = SnapshotStateList<Place>()

    init {
        getAllPlace()
    }

    fun getAllPlace() {
        viewModelScope.launch {
            val places = favoriteRepository.getAllPlace()

            favoritePlaces.run {
                clear()
                addAll(places)
            }
        }
    }

    fun switchFavorite(place: Place) {
        viewModelScope.launch {
            val index = favoritePlaces.indexOf(place)
            val switchedPlace = switchFavoriteUseCase.switchFavorite(
                favoritePlaces[index]
            )

            favoritePlaces[index] = switchedPlace
        }
    }
}