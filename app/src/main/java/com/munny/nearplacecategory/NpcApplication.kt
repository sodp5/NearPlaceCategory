package com.munny.nearplacecategory

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NpcApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val mapClient = NaverMapSdk.NaverCloudPlatformClient(CLIENT_ID)
        NaverMapSdk.getInstance(this).client = mapClient
    }

    companion object {
        private const val CLIENT_ID = "emzppx9dn2"
    }
}