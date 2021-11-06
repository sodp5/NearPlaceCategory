package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.utils.CODE_RESTAURANT
import com.munny.nearplacecategory.utils.Event
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearCategoryListViewModel @Inject constructor(
    private val nearCategoryListRepository: NearCategoryListRepository
) : ViewModel() {
    private val _categoryItems = MutableLiveData<List<CategoryItem>>()
    val categoryItems: LiveData<List<CategoryItem>>
        get() = _categoryItems

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _selectCategoryEvent = MutableLiveData<Event<CategoryItem>>()
    val selectCategoryEvent: LiveData<Event<CategoryItem>>
        get() = _selectCategoryEvent

    private var currentLatLng: LatLng? = null

    fun setLatLng(latitude: Double, longitude: Double) {
        val isInit = currentLatLng == null

        currentLatLng = LatLng(latitude, longitude)

        if (isInit) {
            searchPoi()
        }
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

            _categoryItems.value = map.toList()
                .map { CategoryItem(it.first, it.second) }
                .sortedByDescending { it.placeList.size }
                .also { _isLoading.value = false }
        }
    }

    fun setupArticleImages(categoryItem: CategoryItem) {
        viewModelScope.launch {
            _isLoading.value = true

            val newCategoryItem = categoryItem.copy(
                placeList = categoryItem.placeList.map {
                    it.copy(articleImage = nearCategoryListRepository.getArticleImage(it.name))
                }
            )

            _isLoading.value = false
            _selectCategoryEvent.value = Event(newCategoryItem)
        }
    }
}