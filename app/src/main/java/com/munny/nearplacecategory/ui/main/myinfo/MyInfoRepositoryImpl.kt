package com.munny.nearplacecategory.ui.main.myinfo

import javax.inject.Inject

class MyInfoRepositoryImpl @Inject constructor(
    private val myInfoDataSource: MyInfoDataSource
) : MyInfoRepository {
    override suspend fun getReverseGeocoding(longitude: Double, latitude: Double): String {
        return myInfoDataSource.getReverseGeocoding(longitude, latitude)
    }
}