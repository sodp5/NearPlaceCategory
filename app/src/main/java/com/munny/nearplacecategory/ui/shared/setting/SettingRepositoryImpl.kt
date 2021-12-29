package com.munny.nearplacecategory.ui.shared.setting

import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource
) : SettingRepository {
    override fun setNearDistance(meter: Int) {
        settingDataSource.setNearDistance(meter)
    }

    override fun getNearDistance(): Int {
        return settingDataSource.getNearDistance()
    }
}