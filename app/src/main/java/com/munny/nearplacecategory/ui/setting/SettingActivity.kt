package com.munny.nearplacecategory.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.munny.nearplacecategory.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private val vm: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                settingViewModel = vm
            )
        }

        vm.completeEvent.observeEvent(this) {
            if (it) {
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "거리를 100m 이상으로 설정해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
private fun Screen(
    settingViewModel: SettingViewModel
) {
    val meter by settingViewModel.meter
    val timeCost by settingViewModel.timeCost

    SettingScreen(
        meter = meter,
        timeCost = timeCost,
        onComplete = settingViewModel::complete,
        onTextChanged = settingViewModel::onTextChange
    )
}