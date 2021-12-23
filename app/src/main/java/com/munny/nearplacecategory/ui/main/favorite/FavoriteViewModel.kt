package com.munny.nearplacecategory.ui.main.favorite

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _favoritePlaces = mutableStateListOf<Place>()
    val favoritePlaces: SnapshotStateList<Place>
        get() = _favoritePlaces

    init {
        getAllPlace()
    }

    fun getAllPlace() {
        viewModelScope.launch {
            val places = favoriteRepository.getAllPlace()

            _favoritePlaces.run {
                clear()
                addAll(places)
            }

        }
    }

    fun switchFavorite(place: Place) {
        val liked = place.isLiked.not()

        val index = _favoritePlaces.indexOf(place)
        val switchedPlace = _favoritePlaces[index].copy(
            isLiked = liked
        )

        _favoritePlaces[index] = switchedPlace

        if (liked) {
            addFavorite(switchedPlace)
        } else {
            removeFavorite(switchedPlace)
        }
    }

    private fun addFavorite(place: Place) {
        viewModelScope.launch {
            favoriteRepository.insertPlace(place)
        }
    }

    private fun removeFavorite(place: Place) {
        viewModelScope.launch {
            favoriteRepository.deletePlace(place.id)
        }
    }
}