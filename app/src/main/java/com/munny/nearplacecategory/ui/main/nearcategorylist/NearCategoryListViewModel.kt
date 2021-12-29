package com.munny.nearplacecategory.ui.main.nearcategorylist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.articleimage.ArticleImageRepository
import com.munny.nearplacecategory.utils.CODE_RESTAURANT
import com.munny.nearplacecategory.utils.Event
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearCategoryListViewModel @Inject constructor(
    private val nearCategoryListRepository: NearCategoryListRepository,
    private val articleImageRepository: ArticleImageRepository
) : ViewModel() {
    private val _categoryItems = mutableStateListOf<CategoryItem>()
    val categoryItems: SnapshotStateList<CategoryItem>
        get() = _categoryItems

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean>
        get() = _isLoading

    private val _selectCategoryEvent = MutableLiveData<Event<CategoryItem>>()
    val selectCategoryEvent: LiveData<Event<CategoryItem>>
        get() = _selectCategoryEvent

    private val _searchPoiEvent = MutableLiveData<Event<List<Place>>>()
    val searchPoiEvent: LiveData<Event<List<Place>>>
        get() = _searchPoiEvent

    private var currentLatLng: LatLng? = null

    fun setLatLng(latitude: Double, longitude: Double) {
        currentLatLng = LatLng(latitude, longitude)

        searchPoi()
    }

    private fun searchPoi() {
        val lat = currentLatLng?.latitude ?: return
        val lng = currentLatLng?.longitude ?: return

        viewModelScope.launch {
            _isLoading.value = true

            val placeList = nearCategoryListRepository.getPlaceByCategory(CODE_RESTAURANT, lat, lng)
                .filter { it.categories[0] == "음식점" }
                .map {
                    it.copy(
                        categories = it.categories.toMutableList().apply {
                            removeAt(0)
                        }
                    )
                }


            val map = hashMapOf<String, MutableList<Place>>()
            placeList.forEach { place ->
                (place.categories.getOrNull(0) ?: "기타").let { key ->
                    if (!map.containsKey(key)) {
                        map[key] = mutableListOf()
                    }

                    map[key]?.add(place)
                }
            }

            val newItems = map.toList()
                .map { CategoryItem(it.first, it.second) }
                .sortedByDescending { it.placeList.size }
                .also { _isLoading.value = false }

            _categoryItems.run {
                clear()
                addAll(newItems)
            }


            val allPlaces = categoryItems.map { it.placeList }.flatten()
            _searchPoiEvent.value = Event(allPlaces)
        }
    }

    fun setupArticleImages(categoryItem: CategoryItem) {
        viewModelScope.launch {
            _isLoading.value = true

            val newCategoryItem = categoryItem.copy(
                placeList = categoryItem.placeList.map {
                    it.copy(placeUrl = articleImageRepository.getArticleImageUrl(it.name))
                }
            )

            _isLoading.value = false
            _selectCategoryEvent.value = Event(newCategoryItem)
        }
    }
}