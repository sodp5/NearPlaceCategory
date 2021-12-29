package com.munny.nearplacecategory.ui.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.munny.nearplacecategory.ui.shared.setting.SettingRepository
import com.munny.nearplacecategory.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {
    private val _meter = mutableStateOf(0)
    val meter: State<Int>
        get() = _meter

    val timeCost: State<Int>
        get() {
            val cost = (meter.value + TIME_COST_UNIT - 1) / TIME_COST_UNIT

            return mutableStateOf(cost)
        }

    private val _completeEvent = MutableLiveData<Event<Boolean>>()
    val completeEvent: LiveData<Event<Boolean>>
        get() = _completeEvent

    init {
        val initialValue = settingRepository.getNearDistance()
        _meter.value = initialValue
    }

    fun complete() {
        val isComplete = meter.value >= 100

        if (isComplete) {
            settingRepository.setNearDistance(meter.value)
        }

        _completeEvent.value = Event(isComplete)
    }

    fun onTextChange(value: String) {
        if (!value.isDigitsOnly()) {
            return
        }

        _meter.value = if (value.isEmpty()) {
            0
        } else {
            value.toInt().coerceAtMost(2000)
        }
    }

    companion object {
        private const val TIME_COST_UNIT = 66
    }
}