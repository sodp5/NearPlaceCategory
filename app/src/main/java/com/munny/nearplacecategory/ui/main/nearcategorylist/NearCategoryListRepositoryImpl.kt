package com.munny.nearplacecategory.ui.main.nearcategorylist

import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.setting.SettingDataSource
import javax.inject.Inject

class NearCategoryListRepositoryImpl @Inject constructor(
    private val nearCategoryListDataSource: NearCategoryListDataSource,
    private val settingDataSource: SettingDataSource
) : NearCategoryListRepository {

    override suspend fun getPlaceByCategory(
        categoryCode: String,
        latitude: Double,
        longitude: Double
    ): List<Place> {
        val placeMapper = PlaceMapper()
        val categoryList = mutableListOf<Place>()
        var isEnd = false
        var page = 1
        val size = 15

        while (!isEnd) {
            val categoryResponse = nearCategoryListDataSource.getPlaceByCategory(
                categoryCode,
                latitude,
                longitude,
                page++,
                settingDataSource.getNearDistance(),
                size
            )

            categoryResponse.documents.map(placeMapper::documentToPlace)
                .also {
                    categoryList.addAll(it)
                }

            isEnd = categoryResponse.meta.is_end
        }

        return categoryList
    }
}