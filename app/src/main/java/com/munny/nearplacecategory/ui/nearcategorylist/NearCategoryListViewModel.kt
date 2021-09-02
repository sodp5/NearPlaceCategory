package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.api.KakaoLocalApi
import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import com.munny.nearplacecategory.api.NaverSearchApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NearCategoryListViewModel @Inject constructor(
    private val searchApi: NaverSearchApi,
    private val cloudPlatformApi: NaverCloudPlatformApi,
    private val localApi: KakaoLocalApi
) : ViewModel() {
    fun searchPoi(query: String) {
        viewModelScope.launch {
            searchApi.getPoi(query)
        }
    }

    fun getGeocoding(query: String, lon: Double, lat: Double) {
        viewModelScope.launch {
//            Timber.d("$lon, $lat")
//            cloudPlatformApi.getGeocoding(query, "$lon, $lat")
            localApi.getPlaceByCategory("FD6", lon.toString(), lat.toString(), 1000)
        }
    }
}