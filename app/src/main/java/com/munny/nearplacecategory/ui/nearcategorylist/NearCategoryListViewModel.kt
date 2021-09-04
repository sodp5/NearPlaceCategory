package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.utils.CODE_RESTAURANT
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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
                .filter { it.category?.name == "음식점" }
                .map {
                    it.copy(category = it.category?.category)
                }


            val map = hashMapOf<String, MutableList<Place>>()
            placeList.forEach { place ->
                (place.category?.name ?: "기타").let { key ->
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

}