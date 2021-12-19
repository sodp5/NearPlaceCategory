package com.munny.nearplacecategory.ui.main.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.articleimage.ArticleImageRepository
import com.munny.nearplacecategory.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomPlaceViewModel @Inject constructor(
    private val articleImageRepository: ArticleImageRepository
) : ViewModel() {
    private val _histories = mutableStateListOf<Place>()
    val histories: SnapshotStateList<Place>
        get() = _histories

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

    fun setAllPlace(places: List<Place>) {
        allPlace.run {
            clear()
            addAll(places)
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
            val articleImage = articleImageRepository.getArticleImage(randomPlace.name)

            _recentlyPlace.value?.let {
                _histories.add(0, it)
            }
            _recentlyPlace.value = randomPlace.copy(articleImage = articleImage)

            _isLoading.value = false
        }
    }

    fun onRefresh() {
        _isLoading.value = true

        _recentlyPlace.value = null
        _histories.clear()

        _isLoading.value = false
    }
}