package com.munny.nearplacecategory.ui.main.myinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val myInfoRepository: MyInfoRepository
): ViewModel() {
    private val _myLocation = mutableStateOf("-")
    val myLocation: State<String>
        get() = _myLocation

    private val _refreshEnabled = mutableStateOf(true)
    val refreshEnabled: State<Boolean>
        get() = _refreshEnabled

    fun getMyLocation(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            _myLocation.value = "-"

            val result = myInfoRepository.getReverseGeocoding(longitude, latitude)
            delay(100)

            _myLocation.value = result
        }
    }

    fun setRefreshEnabled(enabled: Boolean) {
        _refreshEnabled.value = enabled
    }
}