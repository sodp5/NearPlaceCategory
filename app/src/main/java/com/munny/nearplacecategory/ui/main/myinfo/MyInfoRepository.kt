package com.munny.nearplacecategory.ui.main.myinfo

interface MyInfoRepository {
    suspend fun getReverseGeocoding(longitude: Double, latitude: Double): String
}