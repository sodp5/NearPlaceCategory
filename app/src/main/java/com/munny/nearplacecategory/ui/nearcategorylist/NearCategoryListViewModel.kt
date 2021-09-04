package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.api.KakaoLocalApi
import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.model.CategoryItem
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
    private val _categoryItems = MutableLiveData<CategoryItem>()
    val categoryItems: LiveData<CategoryItem>
        get() = _categoryItems

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
            val response = nearCategoryListRepository.getPlaceByCategory(CODE_RESTAURANT, lat, lng)
            Timber.d(response.toString())
            Timber.d("size: ${response.size}")
        }
    }

}