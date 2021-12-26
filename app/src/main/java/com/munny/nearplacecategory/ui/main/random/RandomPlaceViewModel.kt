package com.munny.nearplacecategory.ui.main.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.articleimage.ArticleImageRepository
import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import com.munny.nearplacecategory.usecase.GetAllPlaceIdUseCase
import com.munny.nearplacecategory.usecase.SwitchFavoriteUseCase
import com.munny.nearplacecategory.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomPlaceViewModel @Inject constructor(
    private val articleImageRepository: ArticleImageRepository,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val getAllPlaceIdUseCase: GetAllPlaceIdUseCase
) : ViewModel() {
    val histories = SnapshotStateList<Place>()

    private val _recentlyPlace = mutableStateOf<Place?>(null)
    val recentlyPlace: State<Place?>
        get() = _recentlyPlace

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean>
        get() = _isLoading

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>>
        get() = _toastMessage

    private val allPlace = ArrayList<Place>()
    private val favoritePlaceIds = ArrayList<Long>()

    fun setAllPlace(places: List<Place>) {
        allPlace.run {
            clear()
            addAll(places)
        }
    }

    fun setupFavoritePlaceIds() {
        viewModelScope.launch {
            val ids = getAllPlaceIdUseCase.getAllPlaceId()

            favoritePlaceIds.run {
                clear()
                addAll(ids)
            }

            setRecentlyFavorite()
            setHistoriesFavorite()
        }
    }

    private fun setRecentlyFavorite() {
        val id = recentlyPlace.value?.id ?: return

        _recentlyPlace.value = recentlyPlace.value?.copy(
            isLiked = favoritePlaceIds.contains(id)
        )
    }

    private fun setHistoriesFavorite() {
        histories.map {
            it.copy(
                isLiked = favoritePlaceIds.contains(it.id)
            )
        }.run {
            histories.clear()
            histories.addAll(this)
        }
    }


    fun selectRandomPlace() {
        if (allPlace.isEmpty()) {
            _toastMessage.value = Event("아직 장소를 불러오지 못했습니다.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            val randomPlace = allPlace.random()
            val placeUrl = articleImageRepository.getArticleImageUrl(randomPlace.name)

            _recentlyPlace.value?.let {
                histories.add(0, it)
            }
            _recentlyPlace.value = randomPlace.copy(
                placeUrl = placeUrl,
                isLiked = favoritePlaceIds.contains(randomPlace.id)
            )

            _isLoading.value = false
        }
    }

    fun onRefresh() {
        _isLoading.value = true

        _recentlyPlace.value = null
        histories.clear()

        _isLoading.value = false
    }

    fun switchFavorite(place: Place) {
        viewModelScope.launch {
            val index = histories.indexOf(place)

            if (index < 0) {
                recentlyPlace.value?.let {
                    _recentlyPlace.value = switchFavoriteUseCase.switchFavorite(it)
                }
            } else {
                val switchedPlace = switchFavoriteUseCase.switchFavorite(
                    histories[index]
                )

                histories[index] = switchedPlace
            }
        }
    }
}