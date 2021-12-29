package com.munny.nearplacecategory.ui.shared.setting

interface SettingRepository {
    fun setNearDistance(meter: Int)
    fun getNearDistance(): Int
}