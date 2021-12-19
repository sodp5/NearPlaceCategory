package com.munny.nearplacecategory.ui.main.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomPlaceViewModel @Inject constructor() : ViewModel() {
    private val _histories = mutableStateListOf<Place>()
    val histories: SnapshotStateList<Place>
        get() = _histories

    private val _recentlyPlace = mutableStateOf<Place?>(null)
    val recentlyPlace: State<Place?>
        get() = _recentlyPlace

    private val allPlace = ArrayList<Place>()

    fun setAllPlace(places: List<Place>) {
        allPlace.run {
            clear()
            addAll(places)
        }
    }

    fun selectRandomPlace() {
        _recentlyPlace.value?.let {
            _histories.add(0, it)
        }

        _recentlyPlace.value = allPlace.random()
    }
}