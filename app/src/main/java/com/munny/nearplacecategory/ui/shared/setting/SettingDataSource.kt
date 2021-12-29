package com.munny.nearplacecategory.ui.shared.setting

import android.content.SharedPreferences
import androidx.core.content.edit
import com.munny.nearplacecategory.utils.PreferenceKey
import javax.inject.Inject

class SettingDataSource @Inject constructor(
    private val npcPreference: SharedPreferences
) {
    fun setNearDistance(meter: Int) {
        npcPreference.edit {
            putInt(PreferenceKey.KEY_NEAR_DISTANCE, meter)
            commit()
        }
    }

    fun getNearDistance(): Int {
        return npcPreference.getInt(PreferenceKey.KEY_NEAR_DISTANCE, 500)
    }
}